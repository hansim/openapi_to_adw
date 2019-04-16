package com.oracle.adw.service.jpa.impl;

import java.util.List;

import com.oracle.adw.repository.entity.Estate_Real_Apt_Trx;
import com.oracle.adw.repository.entity.Estate_Real_House_Trx;
import com.oracle.adw.repository.entity.Estate_Real_Mansion_Trx;
import com.oracle.adw.repository.jpa.EstateRealTrxAptRepository;
import com.oracle.adw.repository.jpa.EstateRealTrxHouseRepository;
import com.oracle.adw.repository.jpa.EstateRealTrxMansionRepository;
import com.oracle.adw.service.jpa.RealEstateTradeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RealEstateTradeServiceImpl implements RealEstateTradeService {
    @Autowired
    EstateRealTrxAptRepository estateRealTrxAptRepository;

    @Autowired
    EstateRealTrxHouseRepository estateRealTrxHouseRepository;

    @Autowired
    EstateRealTrxMansionRepository estateRealTrxMansionRepository;

    @Override
    public void saveAptTradeAll(List<Estate_Real_Apt_Trx> estate_Real_Trx_list) {
        estateRealTrxAptRepository.saveAll(estate_Real_Trx_list);
    }

    @Override
    public void saveAptTrade(Estate_Real_Apt_Trx estate_Real_Trx) {
        estateRealTrxAptRepository.save(estate_Real_Trx);
    }

    @Override
    public void saveHouseTradeAll(List<Estate_Real_House_Trx> estate_Real_Trx_list) {
        estateRealTrxHouseRepository.saveAll(estate_Real_Trx_list);
    }

    @Override
    public void saveHouseTrade(Estate_Real_House_Trx estate_Real_Trx) {
        estateRealTrxHouseRepository.save(estate_Real_Trx);
    }

    @Override
    public void saveMansionTradeAll(List<Estate_Real_Mansion_Trx> estate_Real_Trx_list) {
        estateRealTrxMansionRepository.saveAll(estate_Real_Trx_list);
    }

    @Override
    public void saveMansionTrade(Estate_Real_Mansion_Trx estate_Real_Trx) {
        estateRealTrxMansionRepository.save(estate_Real_Trx);
    }
 }