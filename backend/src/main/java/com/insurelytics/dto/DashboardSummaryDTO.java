package com.insurelytics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO returned by the /dashboard/summary endpoint.  Contains aggregated
 * values representing the user's portfolio at a glance.
 */
@Data
@Builder
@AllArgsConstructor
public class DashboardSummaryDTO {
    private BigDecimal totalInvestment;
    private BigDecimal currentValue;
    private BigDecimal upcomingPremiums;
    private BigDecimal projectedGrowth;
}