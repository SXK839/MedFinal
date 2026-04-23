package com.diabetes.risk.controller;

import com.diabetes.risk.domain.RiskLevel;
import com.diabetes.risk.service.RiskAssessmentService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assess-risk")
public class RiskAssessmentController {

    private final RiskAssessmentService riskAssessmentService;

    public RiskAssessmentController(RiskAssessmentService riskAssessmentService) {
        this.riskAssessmentService = riskAssessmentService;
    }

    @GetMapping("/{patientId}")
    public RiskLevel assessPatient(
            @PathVariable("patientId") Long patientId) {

        return riskAssessmentService.assessRisk(patientId);
    }
}