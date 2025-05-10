package org.project.backend.appointment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.backend.appointment.dto.request.LoginRequest;
import org.project.backend.appointment.dto.request.RefreshTokenRequest;
import org.project.backend.appointment.dto.request.RegisterRequest;
import org.project.backend.appointment.dto.response.LoginResponse;
import org.project.backend.appointment.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
    authService.register(request);
    return ResponseEntity.ok("Registration successful");
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
    return ResponseEntity.ok(authService.login(request));
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<LoginResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
    return ResponseEntity.ok(authService.refreshToken(request));
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
    authService.logout(authHeader);
    return ResponseEntity.ok("Logout successful");
  }
}
