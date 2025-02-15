package com.gtl.Mutualfunds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gtl.Mutualfunds.model.Transactions;
import com.gtl.Mutualfunds.service.TransactionsService;

import java.util.*;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionsController {

    @Autowired
    private TransactionsService transactionsService;

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody Transactions transaction) {
        try {
            Transactions savedTransaction = transactionsService.saveTransaction(transaction);
            return ResponseEntity.ok(savedTransaction);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to create transaction: " + e.getMessage()));
        }
    }

    // Get all transactions
    @GetMapping
    public ResponseEntity<List<Transactions>> getAllTransactions() {
        return ResponseEntity.ok(transactionsService.getAllTransactions());
    }

    // ✅ Get a transaction by ID (Transaction ID)
    @GetMapping("/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable Long id) {
        return transactionsService.getTransactionById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Get transactions by userId (Fetch all transactions for a user)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transactions>> getTransactionsByUserId(@PathVariable Long userId) {
        List<Transactions> transactions = transactionsService.getTransactionsByUserId(userId);
        return transactions.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(transactions);
    }

    // Update an existing transaction (excluding investDate)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTransaction(@PathVariable Long id, @RequestBody Transactions transaction) {
        try {
            return ResponseEntity.ok(transactionsService.updateTransaction(id, transaction));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    // Delete a transaction
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
        try {
            String message = transactionsService.deleteTransaction(id);
            return ResponseEntity.ok(Map.of("message", message));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    // Global exception handler
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
    }
}
