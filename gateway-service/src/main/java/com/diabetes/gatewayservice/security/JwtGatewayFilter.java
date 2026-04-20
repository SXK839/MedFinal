package com.diabetes.gatewayservice.security;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtGatewayFilter implements GlobalFilter, Ordered {

	@Value("${jwt.secret}")
	private String secretKey;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange,
			org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

		String path = exchange.getRequest().getURI().getPath();

		// Allow auth APIs
		if (path.startsWith("/auth/login") || path.startsWith("/auth/register")) {
			return chain.filter(exchange);
		}

		String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}

		String token = authHeader.substring(7);

		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
		} catch (Exception e) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}

		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return -1; // Run early
	}
}