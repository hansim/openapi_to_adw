package com.oracle.adw.batch.RealEstateTrade.reader;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.adw.batch.RealEstateTrade.dto.MansionTradeDTO;
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
public class RESTRealEstateMansionTradeReader implements ItemReader<List<MansionTradeDTO>> {

    @Autowired
    private AddressSiGuJpaRepository addressSiGuJpaRepository;

    @Value("${open.api.public.data.servicekey}")
    private String serviceKey;

    @Value("${open.api.public.data.mentiontrade.url}")
    private String resturl;

    private RestTemplate restTemplate;
    private int nextAddressSiGuIndex;
    private List<Korea_Address_Sigu> addressSiGu;
    @Value("#{jobParameters[month]}")
    private String month;

    private static final Logger logger = LoggerFactory.getLogger(RESTRealEstateMansionTradeReader.class);

    public RESTRealEstateMansionTradeReader(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        nextAddressSiGuIndex = 0;
    }

    @Override
    public List<MansionTradeDTO> read() throws Exception {

        logger.debug("Reading the information of the next MansionTrade");
        logger.debug("======== nextAddressSiGuIndex : " + nextAddressSiGuIndex + "=========");

        logger.debug("jobparameter month : " +month);
        // 각 지역별로 체크, 모든 지역을 다 조회하여 Read하면 완료. (특정 지역은 데이터가 없을 수 있음)
        if (addressSiGuDataIsNotInitialized()) {
            logger.debug("addressSiGu reset..");
            addressSiGu = addressSiGuJpaRepository.findAll();

            // SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMM");
            // Date time = new Date();
            // currentMonth = format1.format(time);
        }

        Korea_Address_Sigu nextAddressSiGu = null;

        logger.debug("addressSiGu.size() : " + addressSiGu.size());
        if (nextAddressSiGuIndex < addressSiGu.size()) {
            logger.debug("nextAddressSiGuIndex is " + nextAddressSiGuIndex + " and this job is running.");
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

        logger.info(nextAddressSiGu.getSi() + " " + nextAddressSiGu.getGu() + "(" + nextAddressSiGu.getSigu() + ") 연립다세대 실거래가 자료("+month+")를 수집합니다. [" + nextAddressSiGuIndex + "/" +addressSiGu.size() + "]");
        List<MansionTradeDTO> mansionTradeData = fetchMansionTradeDTOFromAPI(nextAddressSiGu.getSigu(), month);

        return mansionTradeData;
        // log.error("[" + sigu + " " + sigu + "] data not found.");

        // return nextAddressSiGu;
    }

    private boolean addressSiGuDataIsNotInitialized() {
        return this.addressSiGu == null;
    }

    // 해당 계약월에 해당하는 전국 아파트 실거래가 정보를 가져온다.
    private List<MansionTradeDTO> fetchMansionTradeDTOFromAPI(int sigu, String month) throws IOException {
        logger.debug("Fetching mansionTrade data from an external API by using the url: {}", resturl);
        JsonNode item = null;

        List<MansionTradeDTO> mansionTradeDTO = new ArrayList<MansionTradeDTO>();

        try {
            URI uri = new URI(resturl + "?serviceKey=" + serviceKey + "&LAWD_CD=" + sigu + "&DEAL_YMD=" + month);

            logger.info("===================================================");
            logger.info("Open API URL :" + uri.toString());
            logger.info("===================================================");
            
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            // LIMITED NUMBER OF SERVICE REQUESTS EXCEEDS ERROR.
            if(root.path("response").path("header").get("resultCode").asText().equals("99")) {
                logger.error(root.path("response").path("header").get("resultMsg").asText(), RESTRealEstateMansionTradeReader.class);
                return null;    //nullpointer exception...
            }

            if(root.path("response").path("body").path("items").has("item")) {
                item = root.path("response").path("body").path("items").get("item");
                
                // Array일 경우와 하나일 경우를 분리해야 함, 한개를 List로 가져올 시 에러
                if(item.isArray()) {
                    
                    mansionTradeDTO = new ObjectMapper().readerFor(new TypeReference<ArrayList<MansionTradeDTO>>() {}).readValue(item);
                } else {
                    mansionTradeDTO.add(new ObjectMapper().readerFor(new TypeReference<MansionTradeDTO>() {}).readValue(item));
                }
            }
            else {
                logger.error("[" + sigu + "] 연립다세대 거래 데이터 없음, 더미 데이터로 처리.");
                return getDummyMansionTrade();
            }

        } catch (Exception e) {
            logger.error("[" + sigu + "] 연립다세대 거래 데이터 없음, 더미 데이터로 처리.");
            return getDummyMansionTrade();
        }

        logger.debug("SiGu : " + sigu + ",mansionTradeDTO.size(): " + mansionTradeDTO.size());
        logger.debug("item.toString() : " + item.toString());
        
        return mansionTradeDTO;
    }

    public List<MansionTradeDTO> getDummyMansionTrade() {

        List<MansionTradeDTO> arr = new ArrayList<MansionTradeDTO>();
        MansionTradeDTO mansionTradeDTO = new MansionTradeDTO();
        mansionTradeDTO.set거래금액("0");
        mansionTradeDTO.set건축년도("0000");
        mansionTradeDTO.set년("0000");
        mansionTradeDTO.set대지권면적("0");
        mansionTradeDTO.set법정동("NA");
        mansionTradeDTO.set연립다세대("NA");
        mansionTradeDTO.set월("0");
        mansionTradeDTO.set일("0");
        mansionTradeDTO.set전용면적("0");
        mansionTradeDTO.set지번("NA");
        mansionTradeDTO.set지역코드("NA");
        mansionTradeDTO.set층("0");
        arr.add(mansionTradeDTO);

        return arr;
    }
}