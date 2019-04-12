
package com.oracle.adw.repository.jpa;

import java.util.List;

import com.oracle.adw.repository.entity.Korea_Address_Sigu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressSiGuJpaRepository extends JpaRepository<Korea_Address_Sigu, Integer> {
    @Query("SELECT k FROM Korea_Address_Sigu k WHERE k.gu IS NOT NULL")
    List<Korea_Address_Sigu> findAll();
}