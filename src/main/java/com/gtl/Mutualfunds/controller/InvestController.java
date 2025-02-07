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
        try {
            return ResponseEntity.ok(investService.getInvestmentById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createInvestment(@RequestBody Invest invest) {
        try {
            return ResponseEntity.ok(investService.saveInvestment(invest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInvestment(@PathVariable Long id, @RequestBody Invest invest) {
        try {
            return ResponseEntity.ok(investService.updateInvestment(id, invest));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInvestment(@PathVariable Long id) {
        try {
            String message = investService.deleteInvestment(id);
            return ResponseEntity.ok(Map.of("message", message));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
