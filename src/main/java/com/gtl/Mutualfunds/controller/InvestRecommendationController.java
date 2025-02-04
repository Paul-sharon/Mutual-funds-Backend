package com.gtl.Mutualfunds.controller;

import com.gtl.Mutualfunds.dto.InvestRecommendationDto;
import com.gtl.Mutualfunds.model.InvestRecommendation;
import com.gtl.Mutualfunds.service.InvestRecommendationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invest-recommendations")
public class InvestRecommendationController {
    private static final Logger logger = LoggerFactory.getLogger(InvestRecommendationController.class);

    @Autowired
    private InvestRecommendationService investRecommendationService;

    // POST API to create a new InvestRecommendation
    @PostMapping("/create")
    public ResponseEntity<InvestRecommendation> createInvestRecommendation(@RequestBody InvestRecommendationDto investRecommendationDto) {
        logger.info("Inside createInvestRecommendation()");
        InvestRecommendation createdRecommendation = investRecommendationService.createInvestRecommendation(investRecommendationDto);
        return new ResponseEntity<>(createdRecommendation, HttpStatus.CREATED);
    }

}
