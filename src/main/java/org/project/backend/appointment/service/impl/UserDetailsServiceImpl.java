package org.project.backend.appointment.service.impl;

import lombok.RequiredArgsConstructor;
import org.project.backend.appointment.entity.Account;
import org.project.backend.appointment.entity.Role;
import org.project.backend.appointment.repository.AccountRepository;
import org.project.backend.appointment.repository.RoleRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final AccountRepository accountRepository;
  private final RoleRepository roleRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account account = accountRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

    List<Role> roles = roleRepository.findAll().stream()
        .filter(role -> role.getUser().getId().equals(account.getUser().getId()))
        .toList();

    List<SimpleGrantedAuthority> authorities = roles.stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
        .collect(Collectors.toList());

    return new User(account.getUsername(), account.getPassword(), authorities);
  }
}
