package com.codewithmosh.store.auth;

import com.codewithmosh.store.users.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@AllArgsConstructor
@Service
public class JwtService {
    private final JwtConfig jwtConfig;
    private final RevokedTokenRepository revokedTokenRepository;

    public Jwt generateAccessToken(User user) {
        return generateToken(user, jwtConfig.getAccessTokenExpiration());
    }

    public Jwt generateRefreshToken(User user) {
        return generateToken(user, jwtConfig.getRefreshTokenExpiration());
    }

    public Jwt parse(String token) {
        try {
            var claims = getClaims(token);
            var hash = hashToken(token);
            if (revokedTokenRepository.existsByTokenHash(hash)) {
                return null;
            }
            return new Jwt(claims, jwtConfig.getSecretKey());
        }
        catch (JwtException exception){
            return null;
        }


    }

    private Jwt generateToken(User user, long tokenExpiration) {
        var claims = Jwts.claims()
                .subject(user.getId().toString())
                .add("name", user.getName())
                .add("email", user.getEmail())
                .add("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .build();

        return new Jwt(claims, jwtConfig.getSecretKey());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String hashToken(String token) {
        try {
            var digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));

            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }

    public void revokeTokens(String token) {
        token = token.replace("Bearer ", "");
        String hashedToken = hashToken(token);
        var revokedToken = new RevokedToken(hashedToken, getClaims(token).getExpiration());
        revokedTokenRepository.save(revokedToken);
    }






}
