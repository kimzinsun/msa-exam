package com.sparta.msa_exam.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class AuthService {
    private final AuthRepository authRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    String secretKey;

    @Value("${spring.application.name}")
    String issuer;

    @Value("${service.jwt.expiration}")
    Long accessExpiration;

    public AuthService(AuthRepository authRepository, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signUp(SignUpReqDto request) {
        User user = new User(request.getUserId(), request.getUsername(), request.getPassword(), request.getRole());
        Optional<User> checkUser = authRepository.findById(user.getUserId());
        if(checkUser.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return authRepository.save(user);
    }

    public String signIn(String userId, String password) {
        User user = authRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

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
