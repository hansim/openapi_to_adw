package com.oracle.adw.service.jpa;

import java.util.List;
import java.util.Optional;

import com.oracle.adw.repository.entity.Sample_Han;

public interface SampleJpaService {
    public Iterable<Sample_Han> getAllSample();
    public Optional<Sample_Han> findById(int id);
    public String saveAll(List<Sample_Han> sample);
    public String save(Sample_Han sample);
     //other methods omitted for brevity
 }