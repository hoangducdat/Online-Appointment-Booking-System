package org.project.backend.appointment.service.impl;

import lombok.RequiredArgsConstructor;
import org.project.backend.appointment.dto.request.NotificationRequest;
import org.project.backend.appointment.dto.response.NotificationResponse;
import org.project.backend.appointment.entity.Notification;
import org.project.backend.appointment.entity.User;
import org.project.backend.appointment.exception.ResourceNotFoundException;
import org.project.backend.appointment.repository.NotificationRepository;
import org.project.backend.appointment.repository.UserRepository;
import org.project.backend.appointment.service.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
  private final NotificationRepository notificationRepository;
  private final UserRepository userRepository;

  @Override
  @Transactional
  public NotificationResponse createNotification(NotificationRequest request) {
    User user = userRepository.findById(request.getUserId())
        .orElseThrow(() -> new ResourceNotFoundException("User", request.getUserId()));

    Notification notification = new Notification();
    notification.setId(UUID.randomUUID().toString());
    notification.setUser(user);
    notification.setMessage(request.getMessage());
    notification.setRead(false);

    notification = notificationRepository.save(notification);
    return mapToResponse(notification);
  }

  @Override
  public List<NotificationResponse> getNotificationsByUser(String userId) {
    return notificationRepository.findByUserId(userId).stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public NotificationResponse markAsRead(String id) {
    Notification notification = notificationRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Notification", id));
    notification.setRead(true);
    notification = notificationRepository.save(notification);
    return mapToResponse(notification);
  }

  @Override
  @Transactional
  public void deleteNotification(String id) {
    Notification notification = notificationRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Notification", id));
    notificationRepository.delete(notification);
  }

  private NotificationResponse mapToResponse(Notification notification) {
    NotificationResponse response = new NotificationResponse();
    response.setId(notification.getId());
    response.setUserId(notification.getUser().getId());
    response.setMessage(notification.getMessage());
    response.setRead(notification.isRead());
    response.setCreatedAt(notification.getCreatedAt());
    return response;
  }
}
