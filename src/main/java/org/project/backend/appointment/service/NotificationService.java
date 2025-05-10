package org.project.backend.appointment.service;

import java.util.List;
import org.project.backend.appointment.dto.request.NotificationRequest;
import org.project.backend.appointment.dto.response.NotificationResponse;

public interface NotificationService {
  NotificationResponse createNotification(NotificationRequest request);
  List<NotificationResponse> getNotificationsByUser(String userId);
  NotificationResponse markAsRead(String id);
  void deleteNotification(String id);
}
