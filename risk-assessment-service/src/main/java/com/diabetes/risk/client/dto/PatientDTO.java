package com.diabetes.risk.client.dto;

import java.time.LocalDate;

public class PatientDTO {

    private Long id;
    private String gender;   // "M" or "F"
    private LocalDate dateOfBirth;

    public Long getId() {
        return id;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
}
