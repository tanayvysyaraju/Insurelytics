package com.insurelytics.controller;

import com.insurelytics.dto.PolicyComparisonDTO;
import com.insurelytics.dto.TimelineEntry;
import com.insurelytics.model.Policy;
import com.insurelytics.model.UserAccount;
import com.insurelytics.repository.PolicyRepository;
import com.insurelytics.service.PolicyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Exposes endpoints for querying and comparing insurance policies.
 */
@RestController
@RequestMapping("/policies")
public class PolicyController {
    private final PolicyRepository policyRepository;
    private final PolicyService policyService;

    public PolicyController(PolicyRepository policyRepository, PolicyService policyService) {
        this.policyRepository = policyRepository;
        this.policyService = policyService;
    }

    @GetMapping
    public ResponseEntity<List<Policy>> getPolicies(Authentication authentication) {
        UserAccount user = (UserAccount) authentication.getPrincipal();
        return ResponseEntity.ok(policyRepository.findByUser(user));
    }

    @GetMapping("/compare")
    public ResponseEntity<List<PolicyComparisonDTO>> compare(@RequestParam(name = "ids") String ids) {
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(String::trim)
                .map(Long::valueOf)
                .collect(Collectors.toList());
        return ResponseEntity.ok(policyService.comparePolicies(idList));
    }

    @GetMapping("/timeline")
    public ResponseEntity<List<TimelineEntry>> timeline(Authentication authentication) {
        UserAccount user = (UserAccount) authentication.getPrincipal();
        return ResponseEntity.ok(policyService.getTimeline(user));
    }
}