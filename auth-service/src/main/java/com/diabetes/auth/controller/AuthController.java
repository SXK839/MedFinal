package com.diabetes.auth.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.*;
import com.diabetes.auth.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public Map<String, String> login() {
        return Map.of("token", JwtUtil.generateToken("user"));
    }
}