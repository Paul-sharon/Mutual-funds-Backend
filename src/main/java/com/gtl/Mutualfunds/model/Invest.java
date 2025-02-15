package com.gtl.Mutualfunds.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "invest")
public class Invest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Lob
    @Column(nullable = false)
    private String companyImg; // Base64 Image

    @Column(nullable = false)
    private double navPercentage;

    @Column(nullable = false)
    private double navRate; // NAV rate

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private double returnPercentage;

    @Column(nullable = false)
    private String returnsIn;

    private String investType;

    @Column(nullable = false)
    private String riskType; // Risk level (Low, Medium, High)

    @Column(nullable = false)
    private String recommendationType; // Recommendation (Buy, Hold, Sell)

    @Column(nullable = false, columnDefinition = "FLOAT DEFAULT 0.0") // Set default value
    private double amount;


    @PrePersist
    public void setDateIfAbsent() {
        if (this.date == null) {
            this.date = LocalDate.now();
        }
    }

    public String getInvestType() {
        return investType;
    }

    public void setInvestType(String investType) {
        this.investType = investType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyImg() {
        return companyImg;
    }

    public void setCompanyImg(String companyImg) {
        this.companyImg = companyImg;
    }

    public double getNavPercentage() {
        return navPercentage;
    }

    public void setNavPercentage(double navPercentage) {
        this.navPercentage = navPercentage;
    }

    public double getNavRate() {
        return navRate;
    }

    public void setNavRate(double navRate) {
        this.navRate = navRate;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getReturnPercentage() {
        return returnPercentage;
    }

    public void setReturnPercentage(double returnPercentage) {
        this.returnPercentage = returnPercentage;
    }

    public String getReturnsIn() {
        return returnsIn;
    }

    public void setReturnsIn(String returnsIn) {
        this.returnsIn = returnsIn;
    }

    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }

    public String getRecommendationType() {
        return recommendationType;
    }

    public void setRecommendationType(String recommendationType) {
        this.recommendationType = recommendationType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
