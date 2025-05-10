package org.project.backend.appointment.repository;

import org.project.backend.appointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, String> {
  List<Appointment> findByProviderIdAndStartTimeBetween(String providerId, LocalDateTime start, LocalDateTime end);
  List<Appointment> findByUserId(String userId);
}
