package org.project.backend.appointment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.backend.appointment.dto.request.NotificationRequest;
import org.project.backend.appointment.dto.response.NotificationResponse;
import org.project.backend.appointment.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
  private final NotificationService notificationService;

  @PostMapping
  @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
  public ResponseEntity<NotificationResponse> createNotification(@Valid @RequestBody NotificationRequest request) {
    NotificationResponse response = notificationService.createNotification(request);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('USER', 'PROVIDER')")
  public ResponseEntity<List<NotificationResponse>> getNotificationsByUser(@RequestParam String userId) {
    List<NotificationResponse> notifications = notificationService.getNotificationsByUser(userId);
    return ResponseEntity.ok(notifications);
  }

  @PutMapping("/{id}/read")
  @PreAuthorize("hasAnyRole('USER', 'PROVIDER')")
  public ResponseEntity<NotificationResponse> markAsRead(@PathVariable String id) {
    NotificationResponse response = notificationService.markAsRead(id);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAnyRole('USER', 'PROVIDER')")
  public ResponseEntity<Void> deleteNotification(@PathVariable String id) {
    notificationService.deleteNotification(id);
    return ResponseEntity.noContent().build();
  }
}
