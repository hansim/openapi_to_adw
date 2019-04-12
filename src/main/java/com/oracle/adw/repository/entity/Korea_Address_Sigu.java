package com.oracle.adw.repository.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "KOREA_ADRESS_SIGU")
public class Korea_Address_Sigu implements Serializable {
    @Column(name = "SIGU")
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sigu;
  
    @Column(name = "SI")
    private String si;

    @Column(name = "GU")
    private String gu;

    public int getSigu() {
        return this.sigu;
    }

    public void setSigu(int sigu) {
        this.sigu = sigu;
    }

    public Korea_Address_Sigu sigu(int sigu) {
        this.sigu = sigu;
        return this;
    }

    public String getSi() {
        return this.si;
    }

    public void setSi(String si) {
        this.si = si;
    }

    public Korea_Address_Sigu si(String si) {
        this.si = si;
        return this;
    }

    public String getGu() {
        return this.gu;
    }

    public void setGu(String gu) {
        this.gu = gu;
    }

    public Korea_Address_Sigu gu(String gu) {
        this.gu = gu;
        return this;
    }
}