package com.diabetes.frontendservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class responsible for defining infrastructure beans.
 * 
 * IMPORTANT: - @Bean methods must NOT be inside controllers - This avoids
 * circular dependency issues in Spring Boot 3
 */
@Configuration
public class RestTemplateConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}