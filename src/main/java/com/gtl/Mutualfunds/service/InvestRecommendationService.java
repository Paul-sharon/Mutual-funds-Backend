package com.gtl.Mutualfunds.service;

import com.gtl.Mutualfunds.controller.InvestRecommendationController;
import com.gtl.Mutualfunds.dto.InvestRecommendationDto;
import com.gtl.Mutualfunds.model.InvestRecommendation;
import com.gtl.Mutualfunds.repository.InvestRecommendationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvestRecommendationService {
    private static final Logger logger = LoggerFactory.getLogger(InvestRecommendationController.class);

    @Autowired
    private InvestRecommendationRepository investRecommendationRepository;

    // Method to create a new InvestRecommendation
    public InvestRecommendation createInvestRecommendation(InvestRecommendationDto investRecommendationDto) {
        logger.info("inside createInvestRecommendation");
        InvestRecommendation investRecommendation = new InvestRecommendation();
        investRecommendation.setAvatarText(investRecommendationDto.getAvatarText());
        investRecommendation.setFundName(investRecommendationDto.getFundName());
        investRecommendation.setReturnPercentage(investRecommendationDto.getReturnPercentage());

        return investRecommendationRepository.save(investRecommendation);
    }
}
