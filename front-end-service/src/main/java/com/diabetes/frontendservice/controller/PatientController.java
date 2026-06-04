package com.diabetes.frontendservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class PatientController {

    private final String GATEWAY_URL = "http://gateway-service:8080";

    @GetMapping("/patients")
    public String getPatients(Model model) {
        RestTemplate restTemplate = new RestTemplate();

        List patients = restTemplate.getForObject(
                GATEWAY_URL + "/api/patients",
                List.class);

        model.addAttribute("patients", patients);
        return "patients";
    }

    @GetMapping("/patients/{id}")
    public String getPatientDetails(@PathVariable Long id, Model model) {

        RestTemplate restTemplate = new RestTemplate();

        Object patient = restTemplate.getForObject(
                GATEWAY_URL + "/api/patients/" + id,
                Object.class);

        List notes = restTemplate.getForObject(
                GATEWAY_URL + "/api/notes/" + id,
                List.class);

        model.addAttribute("patient", patient);
        model.addAttribute("notes", notes);

        return "patient-details";
    }
}