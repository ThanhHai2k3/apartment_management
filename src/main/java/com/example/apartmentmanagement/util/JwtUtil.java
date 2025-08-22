package com.example.apartmentmanagement.util;

import com.example.apartmentmanagement.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String username, List<String> authorities) {
        LocalDateTime ldt = LocalDateTime.now();
        ldt = ldt.plusHours(1);// 1 giờ
        Date exprirationdate = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", authorities) // Lưu tất cả authorities vào claim "roles"
                .setIssuedAt(new Date())
                .setExpiration(exprirationdate)
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public List<SimpleGrantedAuthority> getAuthorities(String token) {
        Claims claims = extractAllClaims(token);
        @SuppressWarnings("unchecked")
        List<String> roleNames = (List<String>) claims.get("roles", List.class); // Xử lý trường hợp null

        return roleNames != null ? roleNames.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()) : Collections.emptyList();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }
}