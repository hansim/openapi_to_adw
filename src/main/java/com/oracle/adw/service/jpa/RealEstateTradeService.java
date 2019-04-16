
package com.oracle.adw.service.jpa;

import java.util.List;

import com.oracle.adw.repository.entity.Estate_Real_Apt_Trx;
import com.oracle.adw.repository.entity.Estate_Real_House_Trx;
import com.oracle.adw.repository.entity.Estate_Real_Mansion_Trx;

public interface RealEstateTradeService {
    public void saveAptTradeAll(List<Estate_Real_Apt_Trx> estate_Real_Trx_list);
    public void saveAptTrade(Estate_Real_Apt_Trx estate_Real_Trx);
    public void saveHouseTradeAll(List<Estate_Real_House_Trx> estate_Real_Trx_list);
    public void saveHouseTrade(Estate_Real_House_Trx estate_Real_Trx);
    public void saveMansionTradeAll(List<Estate_Real_Mansion_Trx> estate_Real_Trx_list);
    public void saveMansionTrade(Estate_Real_Mansion_Trx estate_Real_Trx);
 }