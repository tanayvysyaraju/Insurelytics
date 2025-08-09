package com.insurelytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * Response payload for the real‑time return calculator.  Contains a map of
 * year to projected value.
 */
@Data
@AllArgsConstructor
public class ReturnResponse {
    private Map<Integer, Double> returns;
}