package com.insurelytics.controller;

import com.insurelytics.dto.ReturnRequest;
import com.insurelytics.dto.ReturnResponse;
import com.insurelytics.service.CalculationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes an endpoint for calculating projected returns on a planned investment.
 */
@RestController
@RequestMapping("/calculator")
public class CalculatorController {
    private final CalculationService calculationService;

    public CalculatorController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @PostMapping("/returns")
    public ResponseEntity<ReturnResponse> calculateReturns(@RequestBody ReturnRequest request) {
        return ResponseEntity.ok(calculationService.calculateReturns(request));
    }
}