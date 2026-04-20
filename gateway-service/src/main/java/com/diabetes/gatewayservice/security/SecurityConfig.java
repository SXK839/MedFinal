package com.diabetes.gatewayservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.config.web.server.ServerHttpSecurity;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

		return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
				.authorizeExchange(exchange -> exchange.pathMatchers("/actuator/health").permitAll()
						.pathMatchers("/actuator/**").permitAll().anyExchange().permitAll())
				.build();
	}
}
