package com.gtl.Mutualfunds.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gtl.Mutualfunds.model.Transactions;
import com.gtl.Mutualfunds.repository.TransactionsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionsService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    // Get all transactions
    public List<Transactions> getAllTransactions() {
        return transactionsRepository.findAll();
    }

    // Get transaction by ID
    public Optional<Transactions> getTransactionById(Long id) {
        return transactionsRepository.findById(id);
    }

    public List<Transactions> getTransactionsByUserId(Long userId) {
        return transactionsRepository.findByUserId(userId);
    }

    // Save a new transaction
    public Transactions saveTransaction(Transactions transaction) {
        return transactionsRepository.save(transaction);
    }

    // Update an existing transaction
    public Transactions updateTransaction(Long id, Transactions newTransaction) {
        return transactionsRepository.findById(id).map(transaction -> {
            transaction.setUserId(newTransaction.getUserId());
            transaction.setCompanyName(newTransaction.getCompanyName());
            transaction.setCompanyImg(newTransaction.getCompanyImg());
            transaction.setNavRate(newTransaction.getNavRate());
            transaction.setNavDate(newTransaction.getNavDate());
            transaction.setOrderNo(newTransaction.getOrderNo());
            transaction.setUnits(newTransaction.getUnits());
            transaction.setFolioNo(newTransaction.getFolioNo());
            transaction.setTransactionStatus(newTransaction.getTransactionStatus());
            transaction.setAmount(newTransaction.getAmount());
            return transactionsRepository.save(transaction);
        }).orElseThrow(() -> new RuntimeException("Transaction with ID " + id + " not found"));
    }

    // Delete a transaction
    public String deleteTransaction(Long id) {
        if (transactionsRepository.existsById(id)) {
            transactionsRepository.deleteById(id);
            return "Transaction with ID " + id + " deleted successfully.";
        } else {
            throw new RuntimeException("Transaction with ID " + id + " not found");
        }
    }
}
