package org.project.backend.appointment.repository;

import java.util.Optional;
import org.project.backend.appointment.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
  Optional<Account> findByUsername(String username);
  boolean existsByUsername(String username);
}
