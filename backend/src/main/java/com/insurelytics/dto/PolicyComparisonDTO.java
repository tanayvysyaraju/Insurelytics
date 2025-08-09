package com.insurelytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO representing a simplified view of a policy for comparison purposes.
 */
@Data
@AllArgsConstructor
public class PolicyComparisonDTO {
    private Long id;
    private String name;
    private String type;
    private BigDecimal premium;
    private Integer duration;
    private BigDecimal expectedReturn;
}