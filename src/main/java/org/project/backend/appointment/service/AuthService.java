package org.project.backend.appointment.service;

import org.project.backend.appointment.dto.request.LoginRequest;
import org.project.backend.appointment.dto.request.RefreshTokenRequest;
import org.project.backend.appointment.dto.request.RegisterRequest;
import org.project.backend.appointment.dto.response.LoginResponse;

public interface AuthService {
  void register(RegisterRequest request);
  LoginResponse login(LoginRequest request);
  LoginResponse refreshToken(RefreshTokenRequest request);
  void logout(String authHeader);
}

