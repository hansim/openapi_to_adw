package com.oracle.adw.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SAMPLE")
public class Sample {
    @Column(name = "ID")
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
  
    @Column(name = "NAME", nullable = true, length = 20)
    private String name;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sample id(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sample name(String name) {
        this.name = name;
        return this;
    }
}