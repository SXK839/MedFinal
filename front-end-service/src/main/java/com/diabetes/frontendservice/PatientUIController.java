package com.diabetes.frontendservice;

import com.diabetes.frontendservice.model.Patient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PatientUIController {

	private final RestTemplate restTemplate;

	public PatientUIController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	/* ================= LIST ================= */
	@GetMapping("/patients")
	public String patients(Model model) {
		String url = "http://localhost:8081/patients";
		Patient[] response = restTemplate.getForObject(url, Patient[].class);

		List<Patient> patients = response != null ? Arrays.asList(response) : new ArrayList<>();

		model.addAttribute("patients", patients);
		return "patients";
	}

	/* ================= ADD ================= */
	@GetMapping("/patients/new")
	public String showAddForm(Model model) {
		model.addAttribute("patient", new Patient());
		return "add-patient";
	}

	@PostMapping("/patients")
	public String addPatient(@ModelAttribute Patient patient) {
		restTemplate.postForObject("http://localhost:8081/patients", patient, Patient.class);
		return "redirect:/patients";
	}

	@GetMapping("/patients/edit/{id}")
	public String showEditForm(@PathVariable("id") Long id, Model model) {

		Patient patient = restTemplate.getForObject("http://localhost:8081/patients/" + id, Patient.class);

		model.addAttribute("patient", patient);
		return "edit-patient";
	}

	@GetMapping("/patients/delete/{id}")
	public String deletePatient(@PathVariable("id") Long id) {

		restTemplate.delete("http://localhost:8081/patients/" + id);
		return "redirect:/patients";
	}

	@PostMapping("/patients/update/{id}")
	public String updatePatient(@PathVariable("id") Long id, @ModelAttribute Patient patient) {

		String url = "http://localhost:8081/patients/" + id;
		restTemplate.put(url, patient);

		return "redirect:/patients";
	}
}