package com.diabetes.gatewayservice.security;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final String SECRET = "diabetes-secret-key-32-bytes-long";

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        return http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)

            .authorizeExchange(exchange -> exchange
                // Public endpoints
                .pathMatchers("/auth/**").permitAll()
                .pathMatchers("/actuator/**").permitAll()

                // ALLOW for frontend (IMPORTANT FIX)
                .pathMatchers("/api/patients/**").permitAll()
                .pathMatchers("/api/notes/**").permitAll()

                // Everything else secured
                .anyExchange().authenticated()
            )

            // JWT config (still enabled)
            .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt)

            .build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return NimbusReactiveJwtDecoder
            .withSecretKey(
                new SecretKeySpec(
                    SECRET.getBytes(),
                    "HmacSHA256"
                )
            )
            .build();
    }
}