package com.insurelytics.service;

import com.insurelytics.dto.DashboardSummaryDTO;
import com.insurelytics.model.Investment;
import com.insurelytics.model.Policy;
import com.insurelytics.model.Transaction;
import com.insurelytics.model.UserAccount;
import com.insurelytics.repository.InvestmentRepository;
import com.insurelytics.repository.PolicyRepository;
import com.insurelytics.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

/**
 * Provides aggregated information for the account summary dashboard.
 */
@Service
public class DashboardService {
    private final InvestmentRepository investmentRepository;
    private final PolicyRepository policyRepository;
    private final TransactionRepository transactionRepository;

    public DashboardService(InvestmentRepository investmentRepository,
                            PolicyRepository policyRepository,
                            TransactionRepository transactionRepository) {
        this.investmentRepository = investmentRepository;
        this.policyRepository = policyRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * Computes a summary of the user's portfolio: total investment, current value,
     * upcoming premiums and projected growth.  This is a naive implementation;
     * customise to suit your domain.
     */
    public DashboardSummaryDTO getSummary(UserAccount user) {
        List<Investment> investments = investmentRepository.findByUser(user);
        List<Policy> policies = policyRepository.findByUser(user);
        List<Transaction> txns = transactionRepository.findByUser(user);

        BigDecimal totalInvestment = investments.stream()
                .map(Investment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Compute current value by applying a simple growth formula
        BigDecimal currentValue = totalInvestment;
        for (Investment inv : investments) {
            double years = inv.getDateInvested() != null ?
                    (double) (LocalDate.now().toEpochDay() - inv.getDateInvested().toEpochDay()) / 365.0 : 0.0;
            Double rate = inv.getGrowthRate() != null ? inv.getGrowthRate() : 0.05;
            BigDecimal value = inv.getAmount()
                    .multiply(BigDecimal.valueOf(Math.pow(1 + rate, years)));
            currentValue = currentValue.add(value);
        }

        // Upcoming premiums: sum of policy premiums that have not been paid in last 30 days (simplified)
        BigDecimal upcomingPremiums = policies.stream()
                .map(Policy::getPremium)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // Subtract any premium transactions recorded in last 30 days
        BigDecimal paidPremiums = txns.stream()
                .filter(tx -> "PREMIUM".equalsIgnoreCase(tx.getType()))
                .filter(tx -> tx.getDate().isAfter(LocalDate.now().minusDays(30)))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        upcomingPremiums = upcomingPremiums.subtract(paidPremiums);
        if (upcomingPremiums.compareTo(BigDecimal.ZERO) < 0) {
            upcomingPremiums = BigDecimal.ZERO;
        }

        // Projected growth: sum of expected returns from policies
        BigDecimal projectedGrowth = policies.stream()
                .map(p -> p.getExpectedReturn() != null ? p.getExpectedReturn() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return DashboardSummaryDTO.builder()
                .totalInvestment(totalInvestment.setScale(2, RoundingMode.HALF_UP))
                .currentValue(currentValue.setScale(2, RoundingMode.HALF_UP))
                .upcomingPremiums(upcomingPremiums.setScale(2, RoundingMode.HALF_UP))
                .projectedGrowth(projectedGrowth.setScale(2, RoundingMode.HALF_UP))
                .build();
    }
}