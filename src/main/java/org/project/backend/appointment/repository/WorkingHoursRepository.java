package org.project.backend.appointment.repository;

import org.project.backend.appointment.entity.WorkingHours;
import org.project.backend.appointment.entity.enums.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingHoursRepository extends JpaRepository<WorkingHours, String> {
  List<WorkingHours> findByProviderIdAndDayOfWeek(String providerId, DayOfWeek dayOfWeek);
}
