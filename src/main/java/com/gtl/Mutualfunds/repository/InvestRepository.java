package com.gtl.Mutualfunds.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gtl.Mutualfunds.model.Invest;

public interface InvestRepository extends JpaRepository<Invest, Long> {
}
