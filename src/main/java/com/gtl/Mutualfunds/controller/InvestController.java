package com.gtl.Mutualfunds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gtl.Mutualfunds.model.Invest;
import com.gtl.Mutualfunds.service.InvestService;

import java.util.*;

@RestController
@RequestMapping("/api/investments")
@CrossOrigin(origins = "*")
public class InvestController {

    @Autowired
    private InvestService investService;

    @GetMapping
    public ResponseEntity<List<Invest>> getAllInvestments() {
        return ResponseEntity.ok(investService.getAllInvestments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInvestmentById(@PathVariable Long id) {
        return investService.getInvestmentById(id) != null
                ? ResponseEntity.ok(investService.getInvestmentById(id))
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createInvestment(@RequestBody Invest invest) {
        try {
            return ResponseEntity.ok(investService.saveInvestment(invest));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to create investment: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInvestment(@PathVariable Long id, @RequestBody Invest invest) {
        try {
            return ResponseEntity.ok(investService.updateInvestment(id, invest));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInvestment(@PathVariable Long id) {
        try {
            String message = investService.deleteInvestment(id);
            return ResponseEntity.ok(Map.of("message", message));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
    }
}
