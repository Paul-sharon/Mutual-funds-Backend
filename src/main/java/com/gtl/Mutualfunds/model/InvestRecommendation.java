package com.gtl.Mutualfunds.model;
import jakarta.persistence.*;

@Entity
@Table(name = "invest_recommendations")
public class InvestRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String avatarText;
    private String fundName;
    private String returnPercentage;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvatarText() {
        return avatarText;
    }

    public void setAvatarText(String avatarText) {
        this.avatarText = avatarText;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getReturnPercentage() {
        return returnPercentage;
    }

    public void setReturnPercentage(String returnPercentage) {
        this.returnPercentage = returnPercentage;
    }
}
