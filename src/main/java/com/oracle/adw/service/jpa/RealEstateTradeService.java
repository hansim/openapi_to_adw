
package com.oracle.adw.service.jpa;

import java.util.List;

import com.oracle.adw.repository.entity.Estate_Real_Trx_Apt;

public interface RealEstateTradeService {
     public String saveAll(List<Estate_Real_Trx_Apt> estate_Real_Trx_Apt_list);
     public String save(Estate_Real_Trx_Apt estate_Real_Trx_Apt);
 }