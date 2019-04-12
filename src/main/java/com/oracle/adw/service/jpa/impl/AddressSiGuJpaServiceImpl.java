package com.oracle.adw.service.jpa.impl;

import com.oracle.adw.repository.entity.Korea_Address_Sigu;
import com.oracle.adw.repository.jpa.AddressSiGuJpaRepository;
import com.oracle.adw.service.jpa.AddressSiGuJpaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
 public class AddressSiGuJpaServiceImpl implements AddressSiGuJpaService {
    @Autowired
    AddressSiGuJpaRepository addressSiGuJpaRepository;

    //@Autowired
    //public void setDummyRepository(DummyRepository dummyRepository) {
    //    this.dummyRepository = dummyRepository;
    //}

    @Override
    public Iterable<Korea_Address_Sigu> getAllAddressSiGu() {
        return addressSiGuJpaRepository.findAll();
    }
 }