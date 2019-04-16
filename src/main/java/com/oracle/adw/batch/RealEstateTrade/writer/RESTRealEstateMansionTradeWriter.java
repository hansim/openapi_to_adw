package com.oracle.adw.batch.RealEstateTrade.writer;

import java.util.List;

import com.oracle.adw.repository.entity.Estate_Real_Mansion_Trx;
import com.oracle.adw.service.jpa.RealEstateTradeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RESTRealEstateMansionTradeWriter implements ItemWriter<List<Estate_Real_Mansion_Trx>> {
    private static final Logger logger = LoggerFactory.getLogger(RESTRealEstateHouseTradeWriter.class);

    @Autowired
    RealEstateTradeService realEstateTradeService;

    @Override
    public void write(List<? extends List<Estate_Real_Mansion_Trx>> lists) throws Exception {
        logger.info("Received the information of {} array", lists.size());
        //LOGGER.info("lists.size() : " + lists.size());

        for (List<Estate_Real_Mansion_Trx> list : lists) {
            logger.info("연립다세대 실거래가 데이터 수신, ADW 트랜잭션 시작. [거래 건수 : " + list.size() + " 건]");
            realEstateTradeService.saveMansionTradeAll(list);
            logger.info("연립다세대 실거래가 데이터 수신, ADW 트랜잭션 종료. [거래 건수 : " + list.size() + " 건]");

            // for(Estate_Real_Trx_Apt aptTrade : list) {
            //     estateRealTrxAptRepository.saveAndFlush(aptTrade);
            // }
        }

        //estateRealTrxAptRepository.deleteAllById("201903", "00000");
    }
}