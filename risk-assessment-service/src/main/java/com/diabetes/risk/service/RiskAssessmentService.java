package com.diabetes.risk.service;

import com.diabetes.risk.client.NotesClient;
import com.diabetes.risk.client.PatientClient;
import com.diabetes.risk.client.dto.NoteDTO;
import com.diabetes.risk.client.dto.PatientDTO;
import com.diabetes.risk.domain.RiskLevel;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Collections;

@Service
public class RiskAssessmentService {

    private final PatientClient patientClient;
    private final NotesClient notesClient;

    public RiskAssessmentService(PatientClient patientClient,
                                 NotesClient notesClient) {
        this.patientClient = patientClient;
        this.notesClient = notesClient;
    }

    private static final String[] TRIGGERS = {
            "hemoglobin a1c",
            "microalbumin",
            "height",
            "weight",
            "smoking",
            "abnormal",
            "cholesterol",
            "dizziness",
            "relapse",
            "reaction",
            "antibody"
    };

    /**
     * Assess diabetes risk for a patient.
     */
    public RiskLevel assessRisk(Long patientId) {

        PatientDTO patient;
        List<NoteDTO> notes;

        // ✅ Fetch patient (REQUIRED)
        try {
            patient = patientClient.getPatientById(patientId);
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Patient not found with ID " + patientId
            );
        } catch (FeignException e) {
            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "Patient service unavailable",
                    e
            );
        }

        if (patient == null || patient.getId() == null) {
            throw new IllegalArgumentException(
                "Unable to assess risk: patient not found with ID " + patientId
            );
        }

        // ✅ Fetch notes (OPTIONAL — empty list if none)
        try {
            notes = notesClient.getNotesByPatientId(patientId);
        } catch (FeignException e) {
        	notes = Collections.emptyList(); // Notes failure should NOT block risk assessment
        }

        // ✅ Validate patient data
        if (patient.getDateOfBirth() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Patient date of birth is missing"
            );
        }

        int age = calculateAge(patient.getDateOfBirth());
        int triggerCount = countTriggerTerms(notes);
        String gender = patient.getGender();

        return determineRisk(age, gender, triggerCount);
    }

    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    /**
     * Counts how many trigger terms appear in physician notes.
     */
    private int countTriggerTerms(List<NoteDTO> notes) {

        if (notes == null || notes.isEmpty()) {
            return 0;
        }

        int count = 0;

        for (NoteDTO note : notes) {
            if (note == null || note.getNoteText() == null) {
                continue;
            }

            String content = note.getNoteText().toLowerCase();

            for (String trigger : TRIGGERS) {
                if (content.contains(trigger)) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * Determine risk level according to business rules.
     */
    private RiskLevel determineRisk(int age, String gender, int triggers) {

        if (age < 30) {
            if ("M".equalsIgnoreCase(gender)) {
                if (triggers >= 5) return RiskLevel.EARLY_ONSET;
                if (triggers >= 3) return RiskLevel.IN_DANGER;
            } else {
                if (triggers >= 6) return RiskLevel.EARLY_ONSET;
                if (triggers >= 4) return RiskLevel.IN_DANGER;
            }
        } else {
            if (triggers >= 8) return RiskLevel.EARLY_ONSET;
            if (triggers >= 6) return RiskLevel.IN_DANGER;
            if (triggers >= 2) return RiskLevel.BORDERLINE;
        }

        return RiskLevel.NONE;
    }
}