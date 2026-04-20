package com.diabetes.patientservice.controller;

import com.diabetes.patientservice.security.JwtUtil;
import com.diabetes.patientservice.model.LoginRequest;
import com.diabetes.patientservice.model.JwtResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {

		// DEMO credentials
		if ("user".equals(request.getUsername()) && "password".equals(request.getPassword())) {

			String token = jwtUtil.generateToken(request.getUsername());
			return ResponseEntity.ok(new JwtResponse(token));
		}

		return ResponseEntity.status(401).build();
	}
}