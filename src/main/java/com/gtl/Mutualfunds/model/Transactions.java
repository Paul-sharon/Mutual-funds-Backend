package com.gtl.Mutualfunds.model;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId; // User ID (No foreign key, just a reference)

    @Column(nullable = false)
    private String companyName;

    @Lob
    @Column(nullable = false)
    private String companyImg; // Base64 Image

    @Column(nullable = false)
    private double navRate; // NAV rate

    @Column(nullable = false)
    private LocalDate navDate; // NAV Date (from Invest table)

    @Column(nullable = false)
    private LocalDate investDate; // Investment Date (current date)

    @Column(nullable = false)
    private Long orderNo; // Order Number

    @Column(nullable = false)
    private BigDecimal  units; // Number of units

    @Column(nullable = false)
    private Long folioNo; // Folio Number

    @Column(nullable = false)
    private String transactionStatus; // Transaction status

    @Column(nullable = false)
    private double amount; // Investment Amount

    @PrePersist
    public void setDatesIfAbsent() {
        if (this.investDate == null) {
            this.investDate = LocalDate.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public double getNavRate() {
        return navRate;
    }

    public void setNavRate(double navRate) {
        this.navRate = navRate;
    }

    public LocalDate getNavDate() {
        return navDate;
    }

    public void setNavDate(LocalDate navDate) {
        this.navDate = navDate;
    }

    public LocalDate getInvestDate() {
        return investDate;
    }

    public void setInvestDate(LocalDate investDate) {
        this.investDate = investDate;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal  getUnits() {
        return units;
    }

    public void setUnits(BigDecimal  units) {
        this.units = units;
    }

    public Long getFolioNo() {
        return folioNo;
    }

    public void setFolioNo(Long folioNo) {
        this.folioNo = folioNo;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
