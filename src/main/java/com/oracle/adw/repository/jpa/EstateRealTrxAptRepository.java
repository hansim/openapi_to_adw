package com.oracle.adw.repository.jpa;

import com.oracle.adw.repository.entity.Estate_Real_Trx_Apt;
import com.oracle.adw.repository.entity.Estate_Real_Trx_Apt_Keys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstateRealTrxAptRepository extends JpaRepository<Estate_Real_Trx_Apt, Estate_Real_Trx_Apt_Keys> {
    //@Modifying
    //@Query("delete from Estate_Real_Trx_Apt e where e.계약월 = :month and e.지역코드 = :locationCode")
    //void deleteAllById(@Param("month") String month, @Param("locationCode") String locationCode);
}