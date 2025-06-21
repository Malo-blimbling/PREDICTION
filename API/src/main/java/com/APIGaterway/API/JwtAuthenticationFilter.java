package com.APIGaterway.API;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements WebFilter {

    @Value("${jwt.secret:secret}")
    private String jwtSecret;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // Définir les chemins qui nécessitent une authentification (à adapter selon vos besoins)
        boolean isProtectedPath = path.startsWith("/api/private/") || path.startsWith("/api/user/");

        // Si le chemin n'est pas protégé, laisser passer sans vérifier le token
        if (!isProtectedPath) {
            return chain.filter(exchange);
        }

        // Vérifier la présence du header Authorization pour les chemins protégés
        String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token manquant ou invalide"));
        }
        String token = authHeader.substring(7);
        if (token.isEmpty()) {
            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token vide"));
        }
        return chain.filter(exchange);
    }
}
