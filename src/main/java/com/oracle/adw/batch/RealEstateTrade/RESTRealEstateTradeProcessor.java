package com.oracle.adw.batch.RealEstateTrade;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.oracle.adw.repository.entity.Estate_Real_Trx_Apt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RESTRealEstateTradeProcessor implements ItemProcessor<List<AptTradeDTO>, List<Estate_Real_Trx_Apt>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RESTRealEstateTradeWriter.class);

    @Override
    public List<Estate_Real_Trx_Apt> process(List<AptTradeDTO> list) throws Exception {
        
        SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMM");
        Date time = new Date();
        String currentMonth = format1.format(time);
            
        List<Estate_Real_Trx_Apt> estate_Real_Trx_Apt_List = new ArrayList<Estate_Real_Trx_Apt>();

        for(AptTradeDTO aptTrade : list) {
            Estate_Real_Trx_Apt estate_Real_Trx_Apt = new Estate_Real_Trx_Apt();
            
            estate_Real_Trx_Apt.set거래금액(aptTrade.get거래금액());
            estate_Real_Trx_Apt.set건물명(aptTrade.get아파트());
            estate_Real_Trx_Apt.set건축년도(aptTrade.get건축년도());
            estate_Real_Trx_Apt.set계약월(currentMonth);
            estate_Real_Trx_Apt.set년(aptTrade.get년());
            estate_Real_Trx_Apt.set법정동(aptTrade.get법정동());
            estate_Real_Trx_Apt.set연면적("");
            estate_Real_Trx_Apt.set월(aptTrade.get월());
            estate_Real_Trx_Apt.set일(aptTrade.get일());
            estate_Real_Trx_Apt.set전용면적(aptTrade.get전용면적());
            estate_Real_Trx_Apt.set주택유형("아파트");
            estate_Real_Trx_Apt.set지번(aptTrade.get지번());
            estate_Real_Trx_Apt.set지역코드(aptTrade.get지역코드());
            estate_Real_Trx_Apt.set층(aptTrade.get층());

            estate_Real_Trx_Apt_List.add(estate_Real_Trx_Apt);
        }

        return estate_Real_Trx_Apt_List;
    }

}