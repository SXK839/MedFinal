package com.diabetes.frontendservice.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * DTO used by front-end-service to map data received from patient-service and
 * bind form values.
 */
public class Patient {

	private Long id;
	private String firstName;
	private String lastName;

	// ✅ REQUIRED for <input type="date"> binding
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate dateOfBirth;

	private String gender;
	private String address;
	private String phone;

	public Patient() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}