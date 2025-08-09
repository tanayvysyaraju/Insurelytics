package com.insurelytics.service;

import com.insurelytics.dto.PolicyComparisonDTO;
import com.insurelytics.dto.TimelineEntry;
import com.insurelytics.model.Policy;
import com.insurelytics.model.UserAccount;
import com.insurelytics.repository.PolicyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Encapsulates business logic related to policies, including comparisons and timelines.
 */
@Service
public class PolicyService {
    private final PolicyRepository policyRepository;

    public PolicyService(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    /**
     * Returns a list of DTOs for the policies with the given IDs.  Any missing IDs are ignored.
     */
    public List<PolicyComparisonDTO> comparePolicies(List<Long> ids) {
        return ids.stream()
                .map(policyRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(p -> new PolicyComparisonDTO(
                        p.getId(),
                        p.getName(),
                        p.getType(),
                        p.getPremium(),
                        p.getDuration(),
                        p.getExpectedReturn()))
                .collect(Collectors.toList());
    }

    /**
     * Returns timeline entries for each policy belonging to the given user.  This is a naive
     * implementation which creates annual premium due dates and a final payout at policy end.
     */
    public List<TimelineEntry> getTimeline(UserAccount user) {
        List<Policy> policies = policyRepository.findByUser(user);
        List<TimelineEntry> entries = new ArrayList<>();
        for (Policy p : policies) {
            LocalDate start = p.getStartDate() != null ? p.getStartDate() : LocalDate.now();
            int years = p.getDuration() != null ? p.getDuration() : 1;
            // Add premium due entries for each year
            for (int i = 1; i <= years; i++) {
                entries.add(new TimelineEntry(
                        p.getId(),
                        "Premium due year " + i,
                        start.plusYears(i)));
            }
            // Add final payout entry
            entries.add(new TimelineEntry(
                    p.getId(),
                    "Policy maturity",
                    start.plusYears(years)));
        }
        return entries;
    }
}