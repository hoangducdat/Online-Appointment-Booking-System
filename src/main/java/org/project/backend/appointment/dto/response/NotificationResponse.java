package org.project.backend.appointment.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificationResponse {
  private String id;
  private String userId;
  private String message;
  private boolean isRead;
  private LocalDateTime createdAt;
}
