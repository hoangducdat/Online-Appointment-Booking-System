package org.project.backend.appointment.service.impl;


import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.backend.appointment.dto.request.LoginRequest;
import org.project.backend.appointment.dto.request.RefreshTokenRequest;
import org.project.backend.appointment.dto.request.RegisterRequest;
import org.project.backend.appointment.dto.response.LoginResponse;
import org.project.backend.appointment.entity.Account;
import org.project.backend.appointment.entity.Role;
import org.project.backend.appointment.entity.User;
import org.project.backend.appointment.repository.AccountRepository;
import org.project.backend.appointment.repository.RoleRepository;
import org.project.backend.appointment.repository.UserRepository;
import org.project.backend.appointment.service.AuthService;
import org.project.backend.appointment.service.JwtService;
import org.project.backend.appointment.service.RedisService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final UserRepository userRepository;
  private final AccountRepository accountRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final RedisService redisService;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;

  @Override
  public void register(RegisterRequest request) {
    if (!request.getPassword().equals(request.getConfirmPassword())) {
      throw new IllegalArgumentException("Passwords do not match");
    }

    if (userRepository.existsByEmail(request.getEmail())) {
      throw new IllegalArgumentException("Email already exists");
    }

    if (accountRepository.existsByUsername(request.getUsername())) {
      throw new IllegalArgumentException("Username already exists");
    }

    User user = new User();
    user.setFullName(request.getFullName());
    user.setEmail(request.getEmail());
    user.setPhoneNumber(request.getPhoneNumber());
    userRepository.save(user);

    Account account = new Account();
    account.setUsername(request.getUsername());
    account.setPassword(passwordEncoder.encode(request.getPassword()));
    account.setUser(user);
    accountRepository.save(account);

    Role role = new Role();
    role.setName(Role.RoleName.valueOf(request.getRole() != null ? request.getRole() : "USER"));
    role.setUser(user);
    roleRepository.save(role);
  }

  @Override
  public LoginResponse login(LoginRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
    );

    UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
    String accessToken = jwtService.generateAccessToken(userDetails);
    String refreshToken = jwtService.generateRefreshToken(userDetails);

    redisService.save("access_token:" + request.getUsername(), accessToken,
        jwtService.getAccessTokenTtl(), TimeUnit.MILLISECONDS);
    redisService.save("refresh_token:" + request.getUsername(), refreshToken,
        jwtService.getRefreshTokenTtl(), TimeUnit.MILLISECONDS);

    return new LoginResponse(
        accessToken,
        refreshToken,
        jwtService.getAccessTokenTtl(),
        jwtService.getRefreshTokenTtl(),
        "Bearer"
    );
  }
  @Override
  public LoginResponse refreshToken(RefreshTokenRequest request) {
    String refreshToken = request.getRefreshToken();
    String username = jwtService.extractUsername(refreshToken);

    if (username == null || redisService.isTokenBlacklisted(refreshToken)) {
      throw new IllegalArgumentException("Invalid or blacklisted refresh token");
    }

    String storedToken = redisService.getValue("refresh_token:" + username);
    if (!refreshToken.equals(storedToken)) {
      throw new IllegalArgumentException("Refresh token does not match");
    }

    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    if (!jwtService.isTokenValid(refreshToken, userDetails)) {
      throw new IllegalArgumentException("Refresh token is expired or invalid");
    }

    String newAccessToken = jwtService.generateAccessToken(userDetails);
    String newRefreshToken = jwtService.generateRefreshToken(userDetails);

    // Cập nhật refresh token mới trong Redis
    redisService.save("refresh_token:" + username, newRefreshToken,
        jwtService.getRefreshTokenTtl(), TimeUnit.MILLISECONDS);

    return new LoginResponse(
        newAccessToken,
        newRefreshToken,
        jwtService.getAccessTokenTtl(),
        jwtService.getRefreshTokenTtl(),
        "Bearer"
    );
  }

  @Override
  public void logout(String authHeader) {
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new IllegalArgumentException("Invalid token");
    }

    String jwt = authHeader.substring(7);
    String username = jwtService.extractUsername(jwt);

    if (username != null) {
      redisService.blacklistToken(jwt, jwtService.getAccessTokenTtl());
      redisService.deleteKey("refresh_token:" + username);
    }
  }
}
