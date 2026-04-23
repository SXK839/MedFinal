package com.diabetes.patientservice;

import com.diabetes.patientservice.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientRepository r;

    // ✅ Get all patients
    @GetMapping
    public List<Patient> all() {
        return r.findAll();
    }

    // ✅ Add new patient
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Patient add(@RequestBody Patient patient) {
        return r.save(patient);
    }

    // ✅ Get patient by ID
    @GetMapping("/{patientId}")
    public ResponseEntity<Patient> one(
            @PathVariable("patientId") Long patientId) {

        return r.findById(patientId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Update patient
    @PutMapping("/{patientId}")
    public ResponseEntity<Patient> update(
            @PathVariable("patientId") Long patientId,
            @RequestBody Patient patient) {

        if (!r.existsById(patientId)) {
            return ResponseEntity.notFound().build();
        }

        patient.setId(patientId);
        return ResponseEntity.ok(r.save(patient));
    }

    // ✅ Delete patient
    @DeleteMapping("/{patientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("patientId") Long patientId) {

        if (!r.existsById(patientId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Patient not found with id " + patientId
            );
        }

        r.deleteById(patientId);
    }
}