package com.diabetes.frontendservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Collections;

import com.diabetes.frontendservice.model.Patient;
import com.diabetes.frontendservice.model.Note;

@Controller
public class PatientController {

    @Autowired
    private RestTemplate restTemplate;

    // ✅ Gateway URLs
    private final String PATIENT_URL = "http://gateway-service:8080/patients";
    private final String NOTES_URL   = "http://gateway-service:8080/notes";

    // ✅ LIST PAGE
    @GetMapping("/patients")
    public String listPatients(Model model) {

        Patient[] patientsArray = restTemplate.getForObject(
                PATIENT_URL,
                Patient[].class
        );

        List<Patient> patients = (patientsArray != null)
                ? Arrays.asList(patientsArray)
                : Collections.emptyList();   // ✅ FIXED

        model.addAttribute("patients", patients);

        return "patients";
    }

    // ✅ VIEW PATIENT + NOTES (Sprint 2 ✅)
    @GetMapping("/patients/{id}")
    public String viewPatient(@PathVariable("id") Long id, Model model) {

        // ✅ Get patient
        Patient patient = restTemplate.getForObject(
                PATIENT_URL + "/" + id,
                Patient.class
        );

        // ✅ Get notes
        String notesApiUrl = NOTES_URL + "/patient/" + id;

        Note[] notesArray = restTemplate.getForObject(
                notesApiUrl,
                Note[].class
        );

        List<Note> notes = (notesArray != null)
                ? Arrays.asList(notesArray)
                : Collections.emptyList();   // ✅ FIXED (NO List.of())

        model.addAttribute("patient", patient);
        model.addAttribute("notes", notes);

        return "patient-details";
    }

    // ✅ EDIT PATIENT
    @GetMapping("/patients/edit/{id}")
    public String editPatient(@PathVariable("id") Long id, Model model) {

        Patient patient = restTemplate.getForObject(
                PATIENT_URL + "/" + id,
                Patient.class
        );

        model.addAttribute("patient", patient);

        return "edit-patient";
    }

    // ✅ DELETE PATIENT
    @GetMapping("/patients/delete/{id}")
    public String deletePatient(@PathVariable("id") Long id) {

        restTemplate.delete(PATIENT_URL + "/" + id);

        return "redirect:/patients";
    }

    // ✅ ADD FORM
    @GetMapping("/patients/new")
    public String newPatient(Model model) {

        model.addAttribute("patient", new Patient());
        return "add-patient";
    }

    // ✅ SAVE PATIENT
    @PostMapping("/patients")
    public String savePatient(@ModelAttribute Patient patient) {

        restTemplate.postForObject(
                PATIENT_URL,
                patient,
                Patient.class
        );

        return "redirect:/patients";
    }

    // ✅ UPDATE PATIENT
    @PostMapping("/patients/update/{id}")
    public String updatePatient(@PathVariable("id") Long id,
                               @ModelAttribute Patient patient) {

        restTemplate.put(
                PATIENT_URL + "/" + id,
                patient
        );

        return "redirect:/patients";
    }
}