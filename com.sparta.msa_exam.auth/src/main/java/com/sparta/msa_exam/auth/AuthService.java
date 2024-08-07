package com.sparta.msa_exam.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {
    private final AuthRepository authRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${service.jwt.secret}")
    String secretKey;

    @Value("${spring.application.name}")
    String issuer;

    @Value("${service.jwt.expiration}")
    Long accessExpiration;

    public AuthService(AuthRepository authRepository, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> signUp(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(authRepository.save(user));
    }

    public String signIn(String userId, String password) {
        User user = authRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return createAccessToken(user.getUserId(), user.getRole());
    }

    public String createAccessToken(String userId, String role) {
        return Jwts.builder()
                .claim("user_id", userId)
                .claim("role", role)
                .issuer(issuer)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
