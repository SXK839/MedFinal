package com.diabetes.risk.service;

import com.diabetes.risk.client.NotesClient;
import com.diabetes.risk.client.PatientClient;
import com.diabetes.risk.client.dto.NoteDTO;
import com.diabetes.risk.client.dto.PatientDTO;
import com.diabetes.risk.domain.RiskLevel;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class RiskAssessmentService {

    private final PatientClient patientClient;
    private final NotesClient notesClient;

    public RiskAssessmentService(PatientClient patientClient,
                                 NotesClient notesClient) {
        this.patientClient = patientClient;
        this.notesClient = notesClient;
    }

    public RiskLevel assessRisk(Long patientId) {
        PatientDTO patient = patientClient.getPatientById(patientId);
        List<NoteDTO> notes = notesClient.getNotesByPatientId(patientId);

        int age = calculateAge(patient.getDateOfBirth());
        int triggerCount = countTriggerTerms(notes);
        String gender = patient.getGender();

        return determineRisk(age, gender, triggerCount);
    }

    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

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

    private int countTriggerTerms(List<NoteDTO> notes) {
        int count = 0;

        for (NoteDTO note : notes) {
            String content = note.getContent().toLowerCase();
            for (String trigger : TRIGGERS) {
                if (content.contains(trigger)) {
                    count++;
                }
            }
        }
        return count;
    }
}