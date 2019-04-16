package com.oracle.adw.batch.RealEstateTrade.reader;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.adw.batch.RealEstateTrade.dto.AptTradeDTO;
import com.oracle.adw.repository.entity.Korea_Address_Sigu;
import com.oracle.adw.repository.jpa.AddressSiGuJpaRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Configuration
@StepScope
public class RESTRealEstateAptTradeReader implements ItemReader<List<AptTradeDTO>> {

    @Autowired
    private AddressSiGuJpaRepository addressSiGuJpaRepository;

    @Value("${open.api.public.data.servicekey}")
    private String serviceKey;

    @Value("${open.api.public.data.apttrade.url}")
    private String resturl;

    private RestTemplate restTemplate;
    private int nextAddressSiGuIndex;
    private List<Korea_Address_Sigu> addressSiGu;
    @Value("#{jobParameters[month]}")
    private String month;

    private static final Logger logger = LoggerFactory.getLogger(RESTRealEstateAptTradeReader.class);

    public RESTRealEstateAptTradeReader(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        nextAddressSiGuIndex = 0;
    }

    @Override
    public List<AptTradeDTO> read() throws Exception {

        logger.debug("Reading the information of the next AptTrade");
        logger.debug("======== nextAddressSiGuIndex : " + nextAddressSiGuIndex + "=========");
        logger.debug("jobparameter month : " +month);
        // 각 지역별로 체크, 모든 지역을 다 조회하여 Read하면 완료. (특정 지역은 데이터가 없을 수 있음)
        if (addressSiGuDataIsNotInitialized()) {
            addressSiGu = addressSiGuJpaRepository.findAll();

            logger.info("[RESTRealEstateAptTradeReader.class] addressSiGuData Initialized");
        }

        Korea_Address_Sigu nextAddressSiGu = null;

        logger.debug("addressSiGu.size() : " + addressSiGu.size());
        if (nextAddressSiGuIndex < addressSiGu.size()) {
            nextAddressSiGu = addressSiGu.get(nextAddressSiGuIndex);
            nextAddressSiGuIndex++;
        } else {
            logger.debug("nextAddressSiGuIndex is " + nextAddressSiGuIndex + " and this job has finished.");
            logger.debug("nextAddressSiGuIndex and addressSiGu is initialized");
            month = null;
            addressSiGu = null;
            nextAddressSiGuIndex = 0;
            return null;
        }

        logger.debug("month : " + month);

        logger.info(nextAddressSiGu.getSi() + " " + nextAddressSiGu.getGu() + "(" + nextAddressSiGu.getSigu() + ") 아파트 실거래가 자료("+month+")를 수집합니다. [" + nextAddressSiGuIndex + "/" +addressSiGu.size() + "]");
        List<AptTradeDTO> aptTradeData = fetchAptTradeDTOFromAPI(nextAddressSiGu.getSigu(), month);

        return aptTradeData;
    }

    private boolean addressSiGuDataIsNotInitialized() {
        return this.addressSiGu == null;
    }

    // 해당 계약월에 해당하는 전국 아파트 실거래가 정보를 가져온다.
    private List<AptTradeDTO> fetchAptTradeDTOFromAPI(int sigu, String month) throws IOException {
        logger.info("Fetching AptTrade data from an external API by using the url: {}", resturl);
        JsonNode item = null;

        List<AptTradeDTO> aptTradeDTO = new ArrayList<AptTradeDTO>();

        try {
            URI uri = new URI(resturl + "?serviceKey=" + serviceKey + "&LAWD_CD=" + sigu + "&DEAL_YMD=" + month);

            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            // LIMITED NUMBER OF SERVICE REQUESTS EXCEEDS ERROR.
            if(root.path("response").path("header").get("resultCode").asText().equals("99")) {
                logger.error(root.path("response").path("header").get("resultMsg").asText(), RESTRealEstateAptTradeReader.class);
                return null;    //nullpointer exception...
            }

            if(root.path("response").path("body").path("items").has("item")) {
                item = root.path("response").path("body").path("items").get("item");
                
                // Array일 경우와 하나일 경우를 분리해야 함, 한개를 List로 가져올 시 에러
                if(item.isArray()) {
                    
                    aptTradeDTO = new ObjectMapper().readerFor(new TypeReference<ArrayList<AptTradeDTO>>() {}).readValue(item);
                } else {
                    aptTradeDTO.add(new ObjectMapper().readerFor(new TypeReference<AptTradeDTO>() {}).readValue(item));
                }
            }
            else {
                logger.error("[" + sigu + "] data not found. It will return dummy AptTradeDTO.");
                return getDummyAptTrade();
            }

        } catch (Exception e) {
            logger.error("[" + sigu + "] data not found. It will return dummy AptTradeDTO.");
            return getDummyAptTrade();
        }

        logger.debug("SiGu : " + sigu + ",aptTradeDTO.size(): " + aptTradeDTO.size());
        logger.debug("item.toString() : " + item.toString());
        
        return aptTradeDTO;
    }

    public List<AptTradeDTO> getDummyAptTrade() {

        List<AptTradeDTO> arr = new ArrayList<AptTradeDTO>();
        AptTradeDTO aptTradeDTO = new AptTradeDTO();
        aptTradeDTO.set거래금액("0");
        aptTradeDTO.set건축년도("0000");
        aptTradeDTO.set년("0");
        aptTradeDTO.set법정동("NA");
        aptTradeDTO.set아파트("NA");
        aptTradeDTO.set월("0");
        aptTradeDTO.set일("0");
        aptTradeDTO.set전용면적("0");
        aptTradeDTO.set지번("NA");
        aptTradeDTO.set지역코드("NA");
        aptTradeDTO.set층("0");

        arr.add(aptTradeDTO);

        return arr;
    }
}