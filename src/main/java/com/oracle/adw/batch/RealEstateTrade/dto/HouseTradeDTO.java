package com.oracle.adw.batch.RealEstateTrade.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HouseTradeDTO implements Serializable {
    //private String 계약월;
    private String 거래금액;
    private String 건축년도;
    private String 년;
    private String 대지면적;
    private String 법정동;
    private String 연면적;
    private String 월;
    private String 일;
    private String 주택유형;
    private String 지역코드;
    
    public String get거래금액() {
        return this.거래금액;
    }

    public void set거래금액(String 거래금액) {
        this.거래금액 = 거래금액;
    }

    public HouseTradeDTO 거래금액(String 거래금액) {
        this.거래금액 = 거래금액;
        return this;
    }

    public String get건축년도() {
        return this.건축년도;
    }

    public void set건축년도(String 건축년도) {
        this.건축년도 = 건축년도;
    }

    public HouseTradeDTO 건축년도(String 건축년도) {
        this.건축년도 = 건축년도;
        return this;
    }

    public String get년() {
        return this.년;
    }

    public void set년(String 년) {
        this.년 = 년;
    }

    public HouseTradeDTO 년(String 년) {
        this.년 = 년;
        return this;
    }

    public String get대지면적() {
        return this.대지면적;
    }

    public void set대지면적(String 대지면적) {
        this.대지면적 = 대지면적;
    }

    public HouseTradeDTO 대지면적(String 대지면적) {
        this.대지면적 = 대지면적;
        return this;
    }

    public String get법정동() {
        return this.법정동;
    }

    public void set법정동(String 법정동) {
        this.법정동 = 법정동;
    }

    public HouseTradeDTO 법정동(String 법정동) {
        this.법정동 = 법정동;
        return this;
    }

    public String get연면적() {
        return this.연면적;
    }

    public void set연면적(String 연면적) {
        this.연면적 = 연면적;
    }

    public HouseTradeDTO 연면적(String 연면적) {
        this.연면적 = 연면적;
        return this;
    }

    public String get월() {
        return this.월;
    }

    public void set월(String 월) {
        this.월 = 월;
    }

    public HouseTradeDTO 월(String 월) {
        this.월 = 월;
        return this;
    }

    public String get일() {
        return this.일;
    }

    public void set일(String 일) {
        this.일 = 일;
    }

    public HouseTradeDTO 일(String 일) {
        this.일 = 일;
        return this;
    }

    public String get주택유형() {
        return this.주택유형;
    }

    public void set주택유형(String 주택유형) {
        this.주택유형 = 주택유형;
    }

    public HouseTradeDTO 주택유형(String 주택유형) {
        this.주택유형 = 주택유형;
        return this;
    }

    public String get지역코드() {
        return this.지역코드;
    }

    public void set지역코드(String 지역코드) {
        this.지역코드 = 지역코드;
    }

    public HouseTradeDTO 지역코드(String 지역코드) {
        this.지역코드 = 지역코드;
        return this;
    }
}