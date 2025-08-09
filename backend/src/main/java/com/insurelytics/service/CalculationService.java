package com.insurelytics.service;

import com.insurelytics.dto.ReturnRequest;
import com.insurelytics.dto.ReturnResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Provides methods for calculating projected returns over time based on
 * compound interest formulas.
 */
@Service
public class CalculationService {

    public ReturnResponse calculateReturns(ReturnRequest request) {
        BigDecimal principal = request.getAmount();
        int years = request.getYears() != null ? request.getYears() : 1;
        double rate = request.getRate() != null ? request.getRate() : 0.05;
        double risk = request.getRiskAdjustment() != null ? request.getRiskAdjustment() : 1.0;
        Map<Integer, Double> map = new LinkedHashMap<>();
        for (int year = 1; year <= years; year++) {
            double value = principal.doubleValue() * Math.pow(1 + rate * risk, year);
            map.put(year, BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue());
        }
        return new ReturnResponse(map);
    }
}