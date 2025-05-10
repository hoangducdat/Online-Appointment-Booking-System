package org.project.backend.appointment.repository;

import org.project.backend.appointment.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, String> {
  List<Activity> findByProviderId(String providerId);
}
