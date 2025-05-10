package org.project.backend.appointment.repository;

import org.project.backend.appointment.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
  Optional<Role> findByUserId(String userId);
}
