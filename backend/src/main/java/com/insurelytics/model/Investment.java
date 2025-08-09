package com.insurelytics.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a monetary investment made by a user, optionally tied to a specific policy.
 */
@Entity
@Table(name = "investments")
@Getter
@Setter
@NoArgsConstructor
public class Investment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserAccount user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id")
    private Policy policy;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "date_invested")
    private LocalDate dateInvested;

    /**
     * Annual growth rate expressed as a decimal (e.g. 0.05 for 5%).  Used for ROI projections.
     */
    @Column(name = "growth_rate")
    private Double growthRate;
}