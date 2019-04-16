package com.oracle.adw.batch.RealEstateTrade.processor;

import java.util.ArrayList;
import java.util.List;

import com.oracle.adw.batch.RealEstateTrade.dto.MansionTradeDTO;
import com.oracle.adw.repository.entity.Estate_Real_Mansion_Trx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@StepScope
public class RESTRealEstateMansionTradeProcessor implements ItemProcessor<List<MansionTradeDTO>, List<Estate_Real_Mansion_Trx>> {
    private static final Logger logger = LoggerFactory.getLogger(RESTRealEstateHouseTradeProcessor.class);

    @Value("#{jobParameters[month]}")
    private String month;
    
    @Override
    public List<Estate_Real_Mansion_Trx> process(List<MansionTradeDTO> list) throws Exception {
        
        List<Estate_Real_Mansion_Trx> estate_Real_Mansion_Trx_List = new ArrayList<Estate_Real_Mansion_Trx>();

        for(MansionTradeDTO mansionTradeDTO : list) {
            Estate_Real_Mansion_Trx estate_Real_Mansion_Trx = new Estate_Real_Mansion_Trx();
            
            estate_Real_Mansion_Trx.set계약월(month);
            estate_Real_Mansion_Trx.set거래금액(mansionTradeDTO.get거래금액());
            estate_Real_Mansion_Trx.set건축년도(mansionTradeDTO.get건축년도());
            estate_Real_Mansion_Trx.set년(mansionTradeDTO.get년());
            estate_Real_Mansion_Trx.set대지권면적(mansionTradeDTO.get대지권면적());
            estate_Real_Mansion_Trx.set법정동(mansionTradeDTO.get법정동());
            estate_Real_Mansion_Trx.set연립다세대(mansionTradeDTO.get연립다세대());
            estate_Real_Mansion_Trx.set월(mansionTradeDTO.get월());
            estate_Real_Mansion_Trx.set일(mansionTradeDTO.get일());
            estate_Real_Mansion_Trx.set전용면적(mansionTradeDTO.get전용면적());
            estate_Real_Mansion_Trx.set지번(mansionTradeDTO.get지번());
            estate_Real_Mansion_Trx.set주택유형("연립다세대");
            estate_Real_Mansion_Trx.set지역코드(mansionTradeDTO.get지역코드());

            estate_Real_Mansion_Trx_List.add(estate_Real_Mansion_Trx);
        }

        return estate_Real_Mansion_Trx_List;
    }
}