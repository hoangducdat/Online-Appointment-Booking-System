package org.project.backend.appointment.repository;

import org.project.backend.appointment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByEmail(String email);
  boolean existsByEmail(String email);
}
