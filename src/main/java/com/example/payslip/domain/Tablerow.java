package com.example.payslip.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tablerow {
    @Id
    @GeneratedValue
    private Long id;
    private int minTaxThreshold;
    private int maxTaxThreshold;
    private int taxBase;
    private float taxRate;

    protected Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMinTaxThreshold() {
        return minTaxThreshold;
    }

    public void setMinTaxThreshold(int minTaxThreshold) {
        this.minTaxThreshold = minTaxThreshold;
    }

    public int getMaxTaxThreshold() {
        return maxTaxThreshold;
    }

    public void setMaxTaxThreshold(int maxTaxThreshold) {
        this.maxTaxThreshold = maxTaxThreshold;
    }

    public int getTaxBase() {
        return taxBase;
    }

    public void setTaxBase(int taxBase) {
        this.taxBase = taxBase;
    }

    public float getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(float taxRate) {
        this.taxRate = taxRate;
    }
}

