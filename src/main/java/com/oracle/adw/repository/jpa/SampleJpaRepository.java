package com.oracle.adw.repository.jpa;

import com.oracle.adw.repository.entity.Sample_Han;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleJpaRepository extends JpaRepository<Sample_Han, Integer> {
}