package com.interview.manager.backend.services.auth;
import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtTokenUtil {

    @Value("${spring.jwt.secret}")
    private String secret;

    @Value("${spring.jwt.expiration}")
    private Long expiration;

    private final UserService userService;

    public JwtTokenUtil(UserService userService) {
        this.userService = userService;
    }

    public String generateToken(UUID userId, boolean refreshToken) {
        Map<String, Object> claims = new HashMap<>();
        var user = userService.getById(userId);

        user.ifPresent(u -> {
                claims.put("id", u.getId());
                claims.put("name", u.getName());
                claims.put("surname", u.getSurname());
                claims.put("email", u.getEmail());
            });

        return createToken(claims, userId, refreshToken);
    }

    public Claims extractAllClaims(String token){
        return  Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token).getBody();
    }


    private String createToken(Map<String, Object> claims, UUID userId, boolean refreshToken) {
        return Jwts.builder().setClaims(claims).setSubject(userId.toString())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000 * BooleanUtils.toInteger(refreshToken, 2, 1)))
            .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public Boolean isTokenExpired(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration().before(new Date());
    }

    public Boolean validateToken(String token, UUID userId) {
        Claims claims = extractAllClaims(token);
        String id = claims.get("id").toString();
        return (id.equals(userId.toString()) && !isTokenExpired(token));
    }

    public UUID getUserId(String token) {
        String id = extractAllClaims(token).get("id").toString();
        return UUID.fromString(id);
    }
}
