package com.itma.gestionProjet.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "SECRET"; // Remplace par une clé sécurisée

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    /*
    public List<Long> extractProjectIds(String token) {
        Claims claims = extractClaims(token);
        return (List<Long>) claims.get("projects");
    }


     */
    public List<String> extractRoles(String token) {
        Claims claims = extractClaims(token);
        return (List<String>) claims.get("roles");
    }

    public Long extractUserId(String token) {
        Claims claims = extractClaims(token);
        return Long.parseLong(claims.getSubject());
    }
}


