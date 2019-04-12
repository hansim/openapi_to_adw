package com.oracle.adw.service.jpa.impl;

import java.util.List;

import com.oracle.adw.repository.entity.Estate_Real_Trx_Apt;
import com.oracle.adw.repository.jpa.EstateRealTrxAptRepository;
import com.oracle.adw.service.jpa.RealEstateTradeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RealEstateTradeServiceImpl implements RealEstateTradeService {
    @Autowired
    EstateRealTrxAptRepository estateRealTrxAptRepository;

    @Override
    public String saveAll(List<Estate_Real_Trx_Apt> estate_Real_Trx_Apt_list) {
        estateRealTrxAptRepository.saveAll(estate_Real_Trx_Apt_list);

        return "OK~";
    }

    @Override
    public String save(Estate_Real_Trx_Apt estate_Real_Trx_Apt) {
        estateRealTrxAptRepository.save(estate_Real_Trx_Apt);

        return "OK~";
    }
 }