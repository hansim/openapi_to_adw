package com.oracle.adw.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SAMPLE_HAN")
public class Sample_Han {
    @Column(name = "아이디")
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int 아이디;
  
    @Column(name = "이름", nullable = true, length = 20)
    private String 이름;

    public int get아이디() {
        return this.아이디;
    }

    public void set아이디(int 아이디) {
        this.아이디 = 아이디;
    }

    public Sample_Han 아이디(int 아이디) {
        this.아이디 = 아이디;
        return this;
    }

    public String get이름() {
        return this.이름;
    }

    public void set이름(String 이름) {
        this.이름 = 이름;
    }

    public Sample_Han 이름(String 이름) {
        this.이름 = 이름;
        return this;
    }

}