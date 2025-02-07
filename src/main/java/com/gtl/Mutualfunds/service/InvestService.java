package com.gtl.Mutualfunds.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gtl.Mutualfunds.model.Invest;
import com.gtl.Mutualfunds.repository.InvestRepository;

import java.util.*;

@Service
public class InvestService {

    @Autowired
    private InvestRepository investRepository;

    public List<Invest> getAllInvestments() {
        return investRepository.findAll();
    }

    public Invest getInvestmentById(Long id) {
        return investRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Investment with ID " + id + " not found"));
    }

    public Invest saveInvestment(Invest invest) {
        return investRepository.save(invest);
    }

    public Invest updateInvestment(Long id, Invest newInvest) {
        return investRepository.findById(id).map(invest -> {
            invest.setCompanyName(newInvest.getCompanyName());
            invest.setCompanyImg(newInvest.getCompanyImg());
            invest.setNavPercentage(newInvest.getNavPercentage());
            invest.setReturnPercentage(newInvest.getReturnPercentage());
            invest.setReturnsIn(newInvest.getReturnsIn());
            invest.setInvestType(newInvest.getInvestType());
            return investRepository.save(invest);
        }).orElseThrow(() -> new RuntimeException("Investment with ID " + id + " not found"));
    }

    public String deleteInvestment(Long id) {
        if (investRepository.existsById(id)) {
            investRepository.deleteById(id);
            return "Investment with ID " + id + " deleted successfully.";
        } else {
            throw new RuntimeException("Investment with ID " + id + " not found");
        }
    }
}

