package org.project.backend.appointment.repository;

import java.util.Optional;
import org.project.backend.appointment.entity.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, String> {
  Optional<ServiceProvider> findByUserId(String userId);
}
