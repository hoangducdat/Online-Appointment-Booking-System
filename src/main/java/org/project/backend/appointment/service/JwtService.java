package org.project.backend.appointment.service;

import io.jsonwebtoken.Claims;
import java.util.Date;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
  String extractUsername(String token);
  Date extractExpiration(String token);
  <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
  String generateAccessToken(UserDetails userDetails);
  String generateRefreshToken(UserDetails userDetails);
  boolean isTokenValid(String token, UserDetails userDetails);
  long getAccessTokenTtl();
  long getRefreshTokenTtl();
}
