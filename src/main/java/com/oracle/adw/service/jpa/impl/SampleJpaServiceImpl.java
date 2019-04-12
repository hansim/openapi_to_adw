package com.oracle.adw.service.jpa.impl;

import java.util.List;
import java.util.Optional;

import com.oracle.adw.repository.entity.Sample_Han;
import com.oracle.adw.repository.jpa.SampleJpaRepository;
import com.oracle.adw.service.jpa.SampleJpaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
 public class SampleJpaServiceImpl implements SampleJpaService {
    @Autowired
    SampleJpaRepository sampleJpaRepository;

    //@Autowired
    //public void setDummyRepository(DummyRepository dummyRepository) {
    //    this.dummyRepository = dummyRepository;
    //}

    @Override
    public Iterable<Sample_Han> getAllSample() {
        return sampleJpaRepository.findAll();
    }

    @Override
    public Optional<Sample_Han> findById(int id) {
        return sampleJpaRepository.findById(id);
    }

    @Override
    public String saveAll(List<Sample_Han> sample) {
        sampleJpaRepository.saveAll(sample);

        return "OK";
    }

    @Override
    public String save(Sample_Han sample) {
        sampleJpaRepository.save(sample);

        return "OK";
    }
 }