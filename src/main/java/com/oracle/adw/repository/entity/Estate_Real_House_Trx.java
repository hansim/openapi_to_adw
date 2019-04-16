package com.oracle.adw.repository.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "ESTATE_REAL_TRX_HOUSE")
@IdClass(Estate_Real_House_Trx_Keys.class)
public class Estate_Real_House_Trx implements Serializable {
    @Column(name = "계약월")
    @Id
    private String 계약월;

    @Column(name = "거래금액")
    private String 거래금액;

    @Column(name = "건축년도")
    @Id
    private String 건축년도;

    @Column(name = "년")
    private String 년;

    @Column(name = "대지면적")
    private String 대지면적;

    @Column(name = "법정동")
    @Id
    private String 법정동;

    @Column(name = "연면적")
    private String 연면적;
    
    @Column(name = "월")
    private String 월;

    @Column(name = "일")
    private String 일;

    @Column(name = "주택유형")
    private String 주택유형;

    @Column(name = "지역코드")
    @Id
    private String 지역코드;

    public String get계약월() {
        return this.계약월;
    }

    public void set계약월(String 계약월) {
        this.계약월 = 계약월;
    }

    public Estate_Real_House_Trx 계약월(String 계약월) {
        this.계약월 = 계약월;
        return this;
    }

    public String get거래금액() {
        return this.거래금액;
    }

    public void set거래금액(String 거래금액) {
        this.거래금액 = 거래금액;
    }

    public Estate_Real_House_Trx 거래금액(String 거래금액) {
        this.거래금액 = 거래금액;
        return this;
    }

    public String get건축년도() {
        return this.건축년도;
    }

    public void set건축년도(String 건축년도) {
        this.건축년도 = 건축년도;
    }

    public Estate_Real_House_Trx 건축년도(String 건축년도) {
        this.건축년도 = 건축년도;
        return this;
    }

    public String get년() {
        return this.년;
    }

    public void set년(String 년) {
        this.년 = 년;
    }

    public Estate_Real_House_Trx 년(String 년) {
        this.년 = 년;
        return this;
    }

    public String get대지면적() {
        return this.대지면적;
    }

    public void set대지면적(String 대지면적) {
        this.대지면적 = 대지면적;
    }

    public Estate_Real_House_Trx 대지면적(String 대지면적) {
        this.대지면적 = 대지면적;
        return this;
    }

    public String get법정동() {
        return this.법정동;
    }

    public void set법정동(String 법정동) {
        this.법정동 = 법정동;
    }

    public Estate_Real_House_Trx 법정동(String 법정동) {
        this.법정동 = 법정동;
        return this;
    }

    public String get연면적() {
        return this.연면적;
    }

    public void set연면적(String 연면적) {
        this.연면적 = 연면적;
    }

    public Estate_Real_House_Trx 연면적(String 연면적) {
        this.연면적 = 연면적;
        return this;
    }

    public String get월() {
        return this.월;
    }

    public void set월(String 월) {
        this.월 = 월;
    }

    public Estate_Real_House_Trx 월(String 월) {
        this.월 = 월;
        return this;
    }

    public String get일() {
        return this.일;
    }

    public void set일(String 일) {
        this.일 = 일;
    }

    public Estate_Real_House_Trx 일(String 일) {
        this.일 = 일;
        return this;
    }

    public String get주택유형() {
        return this.주택유형;
    }

    public void set주택유형(String 주택유형) {
        this.주택유형 = 주택유형;
    }

    public Estate_Real_House_Trx 주택유형(String 주택유형) {
        this.주택유형 = 주택유형;
        return this;
    }

    public String get지역코드() {
        return this.지역코드;
    }

    public void set지역코드(String 지역코드) {
        this.지역코드 = 지역코드;
    }

    public Estate_Real_House_Trx 지역코드(String 지역코드) {
        this.지역코드 = 지역코드;
        return this;
    }
}