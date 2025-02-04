package com.gtl.Mutualfunds.dto;

public class InvestRecommendationDto {
    private String avatarText;
    private String fundName;
    private String returnPercentage;

    // Getters and setters
    public String getAvatarText() {
        return avatarText;
    }

    public void setAvatarText(String avatarText ) {
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
