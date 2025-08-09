package com.insurelytics.controller;

import com.insurelytics.dto.DashboardSummaryDTO;
import com.insurelytics.model.UserAccount;
import com.insurelytics.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for account summary dashboard endpoints.
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryDTO> getSummary(Authentication authentication) {
        UserAccount user = (UserAccount) authentication.getPrincipal();
        DashboardSummaryDTO dto = dashboardService.getSummary(user);
        return ResponseEntity.ok(dto);
    }
}