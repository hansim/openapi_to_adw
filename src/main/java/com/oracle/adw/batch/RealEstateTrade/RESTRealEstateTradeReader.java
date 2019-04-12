package com.oracle.adw.batch.RealEstateTrade;

import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.adw.repository.entity.Korea_Address_Sigu;
import com.oracle.adw.repository.jpa.AddressSiGuJpaRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RESTRealEstateTradeReader implements ItemReader<List<AptTradeDTO>> {

    @Autowired
    private AddressSiGuJpaRepository addressSiGuJpaRepository;

    @Value("${open.api.public.data.servicekey}")
    private String serviceKey;

    @Value("${open.api.public.data.apttrade.url}")
    private String resturl;

    private RestTemplate restTemplate;
    private int nextAddressSiGuIndex;
    private List<Korea_Address_Sigu> addressSiGu;
    private String currentMonth;

    private static final Logger log = LoggerFactory.getLogger(AptTradeSvcToADWTask.class);

    RESTRealEstateTradeReader(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        nextAddressSiGuIndex = 0;
    }

    @Override
    public List<AptTradeDTO> read() throws Exception {

        
		
        log.info("Reading the information of the next AptTrade");
        log.info("======== nextAddressSiGuIndex : " + nextAddressSiGuIndex + "=========");

        // 각 지역별로 체크, 모든 지역을 다 조회하여 Read하면 완료. (특정 지역은 데이터가 없을 수 있음)
        if (addressSiGuDataIsNotInitialized()) {
            log.info("addressSiGu reset..");
            addressSiGu = addressSiGuJpaRepository.findAll();

            SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMM");
            Date time = new Date();
            currentMonth = format1.format(time);
        }

        Korea_Address_Sigu nextAddressSiGu = null;

        log.info("addressSiGu.size() : " + addressSiGu.size());
        if (nextAddressSiGuIndex < addressSiGu.size()) {
            log.info("nextAddressSiGuIndex is " + nextAddressSiGuIndex + " and this job is running.");
            nextAddressSiGu = addressSiGu.get(nextAddressSiGuIndex);
            nextAddressSiGuIndex++;
        } else {
            log.info("nextAddressSiGuIndex is " + nextAddressSiGuIndex + " and this job has finished.");
            log.info("nextAddressSiGuIndex and addressSiGu is initialized");
            currentMonth = null;
            addressSiGu = null;
            nextAddressSiGuIndex = 0;
            return null;
        }

        List<AptTradeDTO> aptTradeData = fetchAptTradeDTOFromAPI(nextAddressSiGu.getSigu(), currentMonth);

        return aptTradeData;
        // log.error("[" + sigu + " " + sigu + "] data not found.");

        // return nextAddressSiGu;
    }

    private boolean addressSiGuDataIsNotInitialized() {
        return this.addressSiGu == null;
    }

    // 해당 계약월에 해당하는 전국 아파트 실거래가 정보를 가져온다.
    private List<AptTradeDTO> fetchAptTradeDTOFromAPI(int sigu, String month) throws IOException {
        log.info("Fetching AptTrade data from an external API by using the url: {}", resturl);

        JsonNode item = null;
        try {
            URI uri = new URI(resturl + "?serviceKey=" + serviceKey + "&LAWD_CD=" + sigu + "&DEAL_YMD=" + month);

            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            if(root.path("response").path("body").path("items").has("item")) {
                item = root.path("response").path("body").path("items").get("item");

                log.info("SiGu : " + sigu + ",item.size(): " + item.size());
            }
            else {
                log.error("[" + sigu + "] data not found. It will return dummy AptTradeDTO.");
                return getDummyAptTrade();
            }

        } catch (Exception e) {
            log.error("[" + sigu + "] data not found. It will return dummy AptTradeDTO.");
            return getDummyAptTrade();
        }

        // null이 발생할 수 있음.
        AptTradeDTO[] aptTradeDTO = new ObjectMapper().readerFor(AptTradeDTO[].class).readValue(item);
        
        return Arrays.asList(aptTradeDTO);
        
    }

    public List<AptTradeDTO> getDummyAptTrade() {

        List<AptTradeDTO> arr = new ArrayList<AptTradeDTO>();
        AptTradeDTO aptTradeDTO = new AptTradeDTO();
        aptTradeDTO.set거래금액("거래금액");
        aptTradeDTO.set건축년도("건축년도");
        aptTradeDTO.set년("년");
        aptTradeDTO.set법정동("법정동");
        aptTradeDTO.set아파트("아파트");
        aptTradeDTO.set월("월");
        aptTradeDTO.set일("일");
        aptTradeDTO.set전용면적("전용면적");
        aptTradeDTO.set지번("지번");
        aptTradeDTO.set지역코드("지역코드");
        aptTradeDTO.set층("층");

        arr.add(aptTradeDTO);

        return arr;
    }
}