package com.gtl.Mutualfunds.repository;

import com.gtl.Mutualfunds.model.InvestRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestRecommendationRepository extends JpaRepository<InvestRecommendation, Long> {
    // Fetch all recommendations (you can modify this as per your needs)
    List<InvestRecommendation> findAll();
}
