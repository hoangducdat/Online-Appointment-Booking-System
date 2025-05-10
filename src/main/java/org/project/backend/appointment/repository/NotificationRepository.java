package org.project.backend.appointment.repository;

import org.project.backend.appointment.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, String> {
  List<Notification> findByUserId(String userId);
}
