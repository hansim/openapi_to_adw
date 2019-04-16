package com.oracle.adw.batch.RealEstateTrade.processor;

import java.util.ArrayList;
import java.util.List;

import com.oracle.adw.batch.RealEstateTrade.dto.AptTradeDTO;
import com.oracle.adw.repository.entity.Estate_Real_Apt_Trx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@StepScope
public class RESTRealEstateAptTradeProcessor implements ItemProcessor<List<AptTradeDTO>, List<Estate_Real_Apt_Trx>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RESTRealEstateAptTradeProcessor.class);

    @Value("#{jobParameters[month]}")
    private String month;

    @Override
    public List<Estate_Real_Apt_Trx> process(List<AptTradeDTO> list) throws Exception {
        
        List<Estate_Real_Apt_Trx> estate_Real_Apt_Trx_List = new ArrayList<Estate_Real_Apt_Trx>();

        for(AptTradeDTO aptTrade : list) {
            Estate_Real_Apt_Trx estate_Real_Apt_Trx = new Estate_Real_Apt_Trx();
            
            estate_Real_Apt_Trx.set계약월(month);
            estate_Real_Apt_Trx.set거래금액(aptTrade.get거래금액());
            estate_Real_Apt_Trx.set건축년도(aptTrade.get건축년도());
            estate_Real_Apt_Trx.set년(aptTrade.get년());
            estate_Real_Apt_Trx.set법정동(aptTrade.get법정동());
            estate_Real_Apt_Trx.set아파트(aptTrade.get아파트());
            estate_Real_Apt_Trx.set월(aptTrade.get월());
            estate_Real_Apt_Trx.set일(aptTrade.get일());
            estate_Real_Apt_Trx.set전용면적(aptTrade.get전용면적());
            estate_Real_Apt_Trx.set지번(aptTrade.get지번());
            estate_Real_Apt_Trx.set지역코드(aptTrade.get지역코드());
            estate_Real_Apt_Trx.set층(aptTrade.get층());
            estate_Real_Apt_Trx.set주택유형("아파트");

            estate_Real_Apt_Trx_List.add(estate_Real_Apt_Trx);
        }

        return estate_Real_Apt_Trx_List;
    }

}