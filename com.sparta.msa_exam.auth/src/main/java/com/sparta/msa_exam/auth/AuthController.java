package com.sparta.msa_exam.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/signUp")
    public ResponseEntity<?> signUp(@RequestBody SignUpReqDto request) {
        User user = new User(request.getUserId(), request.getUsername(), request.getPassword(), request.getRole());
        return ResponseEntity.ok(authService.signUp(user));

    }

    @PostMapping("/auth/signIn")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest user) {
        return ResponseEntity.ok(new AuthResponse(authService.signIn(user.getUserId(), user.getPassword())));
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
