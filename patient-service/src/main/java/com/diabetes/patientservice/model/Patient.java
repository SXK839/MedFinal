package com.diabetes.patientservice.model;

import jakarta.persistence.*;
import java.time.*;

@Entity
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	public String firstName, lastName, gender, address, phone;
	public LocalDate dateOfBirth;

//getters/setters omitted for brevity
	public Long getId() {
		return id;
	}

	public void setId(Long i) {
		id = i;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String s) {
		firstName = s;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String s) {
		lastName = s;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate d) {
		dateOfBirth = d;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String g) {
		gender = g;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String a) {
		address = a;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String p) {
		phone = p;
	}
}