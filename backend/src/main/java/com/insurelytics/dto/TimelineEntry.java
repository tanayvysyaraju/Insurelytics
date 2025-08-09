package com.insurelytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * Represents a milestone on a policy timeline.  Each entry is associated with a
 * policy ID and provides a label and date.
 */
@Data
@AllArgsConstructor
public class TimelineEntry {
    private Long policyId;
    private String label;
    private LocalDate date;
}