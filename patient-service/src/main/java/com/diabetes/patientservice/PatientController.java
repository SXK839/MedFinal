package com.diabetes.patientservice;

import org.springframework.web.bind.annotation.*;

import com.diabetes.patientservice.model.Patient;

import java.util.NoSuchElementException;
import java.util.*;
import org.springframework.beans.factory.annotation.*;

@RestController
@RequestMapping("/patients")
public class PatientController {
	@Autowired
	PatientRepository r;

	@GetMapping
	public List<Patient> all() {
		return r.findAll();
	}

	@PostMapping
	public Patient add(@RequestBody Patient p) {
		return r.save(p);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		r.deleteById(id);
	}

	@GetMapping("/{id}")
	public Patient one(@PathVariable Long id) {
		return r.findById(id).orElseThrow(() -> new NoSuchElementException("Patient not found: " + id));
	}

	@PutMapping("/{id}")
	public Patient upd(@PathVariable Long id, @RequestBody Patient p) {
		p.setId(id);
		return r.save(p);
	}
}
