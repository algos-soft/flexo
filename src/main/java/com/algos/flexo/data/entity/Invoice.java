package com.algos.flexo.data.entity;

import com.algos.flexo.beans.Utils;
import com.algos.flexo.utils.Du;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Invoice extends AbstractEntity {

    @Transient
    @Autowired
    private Utils utils;

    @Column(length=65535) // -> MySQL BLOB type
    private byte[] image;
    private String symbol;
    private String name;
    private String category;

    private String exchange;
    private String country;
    private String sector;
    private String industry;
    private Long marketCap;
    private Long ebitda;

    private Float spreadPercent;
    private Float ovnSellDay;
    private Float ovnSellWe;
    private Float ovnBuyDay;
    private Float ovnBuyWe;

    private String unitsFrom;

    private String unitsTo;

    private Integer numUnits;

    private String unitFrequency;

    private String fundamentalUpdateTs;  // last update od Fundamental Data
    private String pricesUpdateTs;  // last update od Prices data



//    @OneToMany(mappedBy = "index", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<IndexUnit> units=new ArrayList<>();

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Long getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(Long marketCap) {
        this.marketCap = marketCap;
    }

    public Long getEbitda() {
        return ebitda;
    }

    public void setEbitda(Long ebitda) {
        this.ebitda = ebitda;
    }

    public Float getSpreadPercent() {
        return spreadPercent;
    }

    public void setSpreadPercent(Float spreadPercent) {
        this.spreadPercent = spreadPercent;
    }

    public Float getOvnSellDay() {
        return ovnSellDay;
    }

    public void setOvnSellDay(Float ovnSellDay) {
        this.ovnSellDay = ovnSellDay;
    }

    public Float getOvnSellWe() {
        return ovnSellWe;
    }

    public void setOvnSellWe(Float ovnSellWe) {
        this.ovnSellWe = ovnSellWe;
    }

    public Float getOvnBuyDay() {
        return ovnBuyDay;
    }

    public void setOvnBuyDay(Float ovnBuyDay) {
        this.ovnBuyDay = ovnBuyDay;
    }

    public Float getOvnBuyWe() {
        return ovnBuyWe;
    }

    public void setOvnBuyWe(Float ovnBuyWe) {
        this.ovnBuyWe = ovnBuyWe;
    }

//    public List<IndexUnit> getUnits() {
//        return units;
//    }
//
//    public void setUnits(List<IndexUnit> units) {
//        this.units = units;
//    }

    public String getUnitsFrom() {
        return unitsFrom;
    }

    public void setUnitsFrom(String unitsFrom) {
        this.unitsFrom = unitsFrom;
    }

    public String getUnitsTo() {
        return unitsTo;
    }

    public void setUnitsTo(String unitsTo) {
        this.unitsTo = unitsTo;
    }

    public Integer getNumUnits() {
        return numUnits;
    }

    public void setNumUnits(Integer numUnits) {
        this.numUnits = numUnits;
    }

    public String getUnitFrequency() {
        return unitFrequency;
    }

    public void setUnitFrequency(String unitFrequency) {
        this.unitFrequency = unitFrequency;
    }

    public String getFundamentalUpdateTs() {
        return fundamentalUpdateTs;
    }

    public void setFundamentalUpdateTs(String fundamentalUpdateTs) {
        this.fundamentalUpdateTs = fundamentalUpdateTs;
    }

    public String getPricesUpdateTs() {
        return pricesUpdateTs;
    }

    public void setPricesUpdateTs(String pricesUpdateTs) {
        this.pricesUpdateTs = pricesUpdateTs;
    }

    public LocalDate getUnitsFromLD(){
        return Du.toLocalDate(unitsFrom);
    }

    public void setUnitsFromLD(LocalDate localDate) {
        this.unitsFrom = Du.toUtcString(localDate);
    }

    public LocalDate getUnitsToLD(){
        return Du.toLocalDate(unitsTo);
    }

    public void setUnitsToLD(LocalDate localDate) {
        this.unitsTo = Du.toUtcString(localDate);
    }

    @Override
    public String toString() {
        return "MarketIndex{" +
                "id='" + getId() + '\'' +
                ", symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
