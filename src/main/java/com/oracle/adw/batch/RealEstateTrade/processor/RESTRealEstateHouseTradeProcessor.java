package com.oracle.adw.batch.RealEstateTrade.processor;

import java.util.ArrayList;
import java.util.List;

import com.oracle.adw.batch.RealEstateTrade.dto.HouseTradeDTO;
import com.oracle.adw.repository.entity.Estate_Real_House_Trx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@StepScope
public class RESTRealEstateHouseTradeProcessor implements ItemProcessor<List<HouseTradeDTO>, List<Estate_Real_House_Trx>> {
    private static final Logger logger = LoggerFactory.getLogger(RESTRealEstateHouseTradeProcessor.class);

    @Value("#{jobParameters[month]}")
    private String month;
    
    @Override
    public List<Estate_Real_House_Trx> process(List<HouseTradeDTO> list) throws Exception {
        
        List<Estate_Real_House_Trx> estate_Real_House_Trx_List = new ArrayList<Estate_Real_House_Trx>();

        for(HouseTradeDTO houseTradeDTO : list) {
            Estate_Real_House_Trx estate_Real_House_Trx = new Estate_Real_House_Trx();
            
            estate_Real_House_Trx.set계약월(month);
            estate_Real_House_Trx.set거래금액(houseTradeDTO.get거래금액());
            estate_Real_House_Trx.set건축년도(houseTradeDTO.get건축년도());
            estate_Real_House_Trx.set년(houseTradeDTO.get년());
            estate_Real_House_Trx.set대지면적(houseTradeDTO.get대지면적());
            estate_Real_House_Trx.set법정동(houseTradeDTO.get법정동());
            estate_Real_House_Trx.set연면적(houseTradeDTO.get연면적());
            estate_Real_House_Trx.set월(houseTradeDTO.get월());
            estate_Real_House_Trx.set일(houseTradeDTO.get일());
            estate_Real_House_Trx.set주택유형(houseTradeDTO.get주택유형());
            estate_Real_House_Trx.set지역코드(houseTradeDTO.get지역코드());

            estate_Real_House_Trx_List.add(estate_Real_House_Trx);
        }

        return estate_Real_House_Trx_List;
    }
}