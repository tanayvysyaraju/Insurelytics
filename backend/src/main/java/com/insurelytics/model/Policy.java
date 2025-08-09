package com.insurelytics.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents an insurance policy held by a user.  Policies can be of different types
 * (term, whole, annuity) and carry premiums and expected returns.
 */
@Entity
@Table(name = "policies")
@Getter
@Setter
@NoArgsConstructor
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private BigDecimal premium;

    /**
     * Policy duration in years.
     */
    @Column(nullable = false)
    private Integer duration;

    @Column(name = "expected_return")
    private BigDecimal expectedReturn;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserAccount user;
}