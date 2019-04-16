package com.oracle.adw.batch.RealEstateTrade.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AptTradeDTO implements Serializable {
    //private String 계약월;
    private String 거래금액;
    private String 건축년도;
    private String 년;
    private String 법정동;
    private String 아파트;
    private String 월;
    private String 일;
    private String 전용면적;
    private String 지번;
    private String 지역코드;
    private String 층;

    // public String get계약월() {
    //     return this.계약월;
    // }

    // public void set계약월(String 계약월) {
    //     this.계약월 = 계약월;
    // }

    // public AptTradeDTO 계약월(String 계약월) {
    //     this.계약월 = 계약월;
    //     return this;
    // }

    public String get거래금액() {
        return this.거래금액;
    }

    public void set거래금액(String 거래금액) {
        this.거래금액 = 거래금액;
    }

    public AptTradeDTO 거래금액(String 거래금액) {
        this.거래금액 = 거래금액;
        return this;
    }

    public String get건축년도() {
        return this.건축년도;
    }

    public void set건축년도(String 건축년도) {
        this.건축년도 = 건축년도;
    }

    public AptTradeDTO 건축년도(String 건축년도) {
        this.건축년도 = 건축년도;
        return this;
    }

    public String get년() {
        return this.년;
    }

    public void set년(String 년) {
        this.년 = 년;
    }

    public AptTradeDTO 년(String 년) {
        this.년 = 년;
        return this;
    }

    public String get법정동() {
        return this.법정동;
    }

    public void set법정동(String 법정동) {
        this.법정동 = 법정동;
    }

    public AptTradeDTO 법정동(String 법정동) {
        this.법정동 = 법정동;
        return this;
    }

    public String get아파트() {
        return this.아파트;
    }

    public void set아파트(String 아파트) {
        this.아파트 = 아파트;
    }

    public AptTradeDTO 아파트(String 아파트) {
        this.아파트 = 아파트;
        return this;
    }

    public String get월() {
        return this.월;
    }

    public void set월(String 월) {
        this.월 = 월;
    }

    public AptTradeDTO 월(String 월) {
        this.월 = 월;
        return this;
    }

    public String get일() {
        return this.일;
    }

    public void set일(String 일) {
        this.일 = 일;
    }

    public AptTradeDTO 일(String 일) {
        this.일 = 일;
        return this;
    }

    public String get전용면적() {
        return this.전용면적;
    }

    public void set전용면적(String 전용면적) {
        this.전용면적 = 전용면적;
    }

    public AptTradeDTO 전용면적(String 전용면적) {
        this.전용면적 = 전용면적;
        return this;
    }

    public String get지번() {
        return this.지번;
    }

    public void set지번(String 지번) {
        this.지번 = 지번;
    }

    public AptTradeDTO 지번(String 지번) {
        this.지번 = 지번;
        return this;
    }

    public String get지역코드() {
        return this.지역코드;
    }

    public void set지역코드(String 지역코드) {
        this.지역코드 = 지역코드;
    }

    public AptTradeDTO 지역코드(String 지역코드) {
        this.지역코드 = 지역코드;
        return this;
    }

    public String get층() {
        return this.층;
    }

    public void set층(String 층) {
        this.층 = 층;
    }

    public AptTradeDTO 층(String 층) {
        this.층 = 층;
        return this;
    }
}