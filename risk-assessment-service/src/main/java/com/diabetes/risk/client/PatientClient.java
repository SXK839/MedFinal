package com.diabetes.risk.client;

import com.diabetes.risk.client.dto.PatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "patient-service",
    url = "http://localhost:8081"
)
public interface PatientClient {

    @GetMapping("/patients/{id}")
    PatientDTO getPatientById(@PathVariable("id") Long id);
}