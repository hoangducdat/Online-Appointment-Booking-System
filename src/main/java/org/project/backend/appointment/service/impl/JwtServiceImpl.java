package org.project.backend.appointment.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.project.backend.appointment.service.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {
  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.access.token.expiration}")
  private long accessTokenTtl;

  @Value("${jwt.refresh.token.expiration}")
  private long refreshTokenTtl;

  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(secret.getBytes());
  }

  @Override
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  @Override
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  @Override
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  @Override
  public String generateAccessToken(UserDetails userDetails) {
    return generateToken(userDetails, accessTokenTtl);
  }

  @Override
  public String generateRefreshToken(UserDetails userDetails) {
    return generateToken(userDetails, refreshTokenTtl);
  }

  private String generateToken(UserDetails userDetails, long ttl) {
    Map<String, Object> claims = new HashMap<>();
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + ttl))
        .signWith(SignatureAlgorithm.HS256, getSigningKey())
        .compact();
  }

  @Override
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  @Override
  public long getAccessTokenTtl() {
    return accessTokenTtl;
  }

  @Override
  public long getRefreshTokenTtl() {
    return refreshTokenTtl;
  }
}
