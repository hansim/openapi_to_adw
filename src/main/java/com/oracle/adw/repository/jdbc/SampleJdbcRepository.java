package com.oracle.adw.repository.jdbc;

import java.util.List;
import java.util.Optional;

import com.oracle.adw.repository.entity.Sample;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleJdbcRepository extends CrudRepository<Sample, Integer> {
    @Query("select id, name from Sample d where d.id = :id")
    Optional<Sample> getSample(@Param("id") int id);
    
    @Query("select id, name from Sample")
    List<Sample> getAllSample();
}