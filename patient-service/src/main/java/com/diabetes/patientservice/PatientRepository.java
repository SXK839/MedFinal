package com.diabetes.patientservice;

import org.springframework.data.jpa.repository.*;

import com.diabetes.patientservice.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}