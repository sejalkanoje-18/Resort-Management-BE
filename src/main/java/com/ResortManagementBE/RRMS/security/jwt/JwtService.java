package com.ResortManagementBE.RRMS.security.jwt;

import com.ResortManagementBE.RRMS.auth.entity.Role;
import com.ResortManagementBE.RRMS.security.principal.CustomUserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;

    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                jwtProperties.getSecret()
                        .getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateToken(CustomUserPrincipal principal) {
        var builder = Jwts.builder()
                .subject(principal.getUsername())
                .claim("userId", principal.getUserId())
                .claim("role", principal.getRole().name());

        if(principal.getTenantId()!= null) {
            builder.claim("tenantId", principal.getTenantId());
        }

        Date now = new Date();

        Date expiration = new Date(
                now.getTime() + jwtProperties.getExpiration()
        );

        return builder
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    public Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractClaim(String token) {

        return extractAllClaims(token)
                .getSubject();
    }

    public Long extractUserId(String token) {
        Number userId = extractAllClaims(token)
                .get("userId", Number.class);

        return userId != null
                ? userId.longValue()
                : null;
    }

    public Role extractRole(String token) {

        String role = extractAllClaims(token)
                .get("role", String.class);

        return Role.valueOf(role);
    }

    public Long extractTenantId(String token) {
        Number tenantId = extractAllClaims(token)
                .get("tenantId", Number.class);

        return tenantId != null
                ? tenantId.longValue()
                : null;
    }

    public boolean isTokenValid(String token) {

        try{
            extractAllClaims(token);
            return true;
        } catch(Exception e){
            return false;
        }
    }
}
