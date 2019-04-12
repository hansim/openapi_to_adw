package com.oracle.adw.service.jdbc;

import java.util.List;
import java.util.Optional;

import com.oracle.adw.repository.entity.Sample;

public interface SampleJdbcService {
    public List<Sample> getAllSample();
    public Optional<Sample> getSample(int id);
     //other methods omitted for brevity
 }