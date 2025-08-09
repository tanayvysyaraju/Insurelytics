package com.insurelytics.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Request payload for the real‑time return calculator.  Represents a user's
 * planned investment and horizon (in years).
 */
@Data
public class ReturnRequest {
    private BigDecimal amount;
    private Integer years;
    /**
     * Optional annual growth rate (0.05 = 5%).  If omitted, a default of 5% is used.
     */
    private Double rate;
    /**
     * Optional risk adjustment factor (e.g. 0.9 reduces the return by 10%).
     */
    private Double riskAdjustment;
}