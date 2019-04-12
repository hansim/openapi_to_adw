package com.oracle.adw.service.jdbc;

import java.util.List;
import java.util.Optional;

import com.oracle.adw.repository.entity.Sample;
import com.oracle.adw.repository.jdbc.SampleJdbcRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
 public class SampleJdbcServiceImpl implements SampleJdbcService {
    @Autowired
    SampleJdbcRepository sampleJdbcRepository;

    //@Autowired
    //public void setDummyRepository(DummyRepository dummyRepository) {
    //    this.dummyRepository = dummyRepository;
    //}
 
    @Override
    public Optional<Sample> getSample(int id) {
        return sampleJdbcRepository.getSample(id);
    }

    @Override
    public List<Sample> getAllSample() {
        return sampleJdbcRepository.getAllSample();
    }
}