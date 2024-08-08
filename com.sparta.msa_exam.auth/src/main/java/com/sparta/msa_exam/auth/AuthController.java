package com.sparta.msa_exam.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthController {

    private final AuthService authService;

    @Value("${server.port}")
    String port;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/signUp")
    public ResponseEntity<?> signUp(@RequestBody SignUpReqDto request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("ServerPort", port);

        try {
            User user = authService.signUp(request);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .headers(headers)
                    .body(e.getMessage());
        }

    }

    @PostMapping("auth/signIn")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest request) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("ServerPort", port);

            try {
                String accessToken = authService.signIn(request.getUserId(), request.getPassword());
                return ResponseEntity.ok()
                        .headers(headers)
                        .body(new AuthResponse(accessToken));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest()
                        .headers(headers)
                        .body(e.getMessage());
            }
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class AuthResponse {
        private String access_token;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class SignInRequest {
        private String userId;
        private String password;
    }

}
