package com.oracle.adw.batch.RealEstateTrade;

import java.util.List;

import com.oracle.adw.repository.entity.Estate_Real_Trx_Apt;
import com.oracle.adw.service.jpa.RealEstateTradeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RESTRealEstateTradeWriter implements ItemWriter<List<Estate_Real_Trx_Apt>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RESTRealEstateTradeWriter.class);

    @Autowired
    RealEstateTradeService realEstateTradeService;

    
    @Override
    public void write(List<? extends List<Estate_Real_Trx_Apt>> lists) throws Exception {
        LOGGER.info("Received the information of {} array", lists.size());
        LOGGER.info("lists.size() : " + lists.size());

        for (List<Estate_Real_Trx_Apt> list : lists) {
            LOGGER.info("list.size() : " + list.size());
            realEstateTradeService.saveAll(list);

            // for(Estate_Real_Trx_Apt aptTrade : list) {
            //     estateRealTrxAptRepository.saveAndFlush(aptTrade);
            // }
        }

        //estateRealTrxAptRepository.deleteAllById("201903", "00000");
    }
}