
package com.oracle.adw.batch.RealEstateTrade;

import java.net.URI;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.adw.repository.entity.Korea_Address_Sigu;
import com.oracle.adw.repository.jpa.AddressSiGuJpaRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AptTradeSvcToADWTask implements Tasklet {
    
    private static final Logger log = LoggerFactory.getLogger(AptTradeSvcToADWTask.class);

    private String serviceKey;
    private String resturl;
    private AddressSiGuJpaRepository addressSiGuJpaRepository;

    public AptTradeSvcToADWTask(String resturl, String serviceKey, AddressSiGuJpaRepository addressSiGuJpaRepository) {
        this.resturl = resturl;
        this.serviceKey = serviceKey;
        this.addressSiGuJpaRepository = addressSiGuJpaRepository;
    }

    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception
    {
        System.out.println("AptTradeSvcToADWTask start..");
        

        List<Korea_Address_Sigu> addressSiGu = addressSiGuJpaRepository.findAll();

        for(Korea_Address_Sigu sigu : addressSiGu) {
            log.info("sigu : " + sigu.getSigu());
        }

        RestTemplate restTemplate = new RestTemplate();
        
        // URI로 전달하지 않으면 Key 오류가 발생
        URI uri = new URI(resturl + "?serviceKey=" +serviceKey + "&LAWD_CD=11110&DEAL_YMD=201512");
        
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        //AptTradeDTO[] aptTradeData = response.getBody();
        //Arrays.asList(aptTradeData);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode item = root.path("response").path("body").path("items").get("item");

        AptTradeDTO [] aptTradeDTO = new ObjectMapper().readerFor(AptTradeDTO[].class).readValue(item);

        log.info("aptTradeDTO.length : " + aptTradeDTO.length);

        for(AptTradeDTO aptTrade : aptTradeDTO) {
            log.info(aptTrade.get거래금액());
        }
        
        System.out.println("AptTradeSvcToADWTask done..");
        return RepeatStatus.FINISHED;
    }   
}