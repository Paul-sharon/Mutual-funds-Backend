package com.gtl.Mutualfunds.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gtl.Mutualfunds.model.Transactions;

import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {

    // Custom query to find transactions by user ID
    List<Transactions> findByUserId(Long userId);

    // Custom query to find transactions by status
    List<Transactions> findByTransactionStatus(String status);
}
