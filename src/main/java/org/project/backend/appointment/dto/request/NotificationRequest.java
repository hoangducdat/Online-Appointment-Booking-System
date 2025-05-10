package org.project.backend.appointment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NotificationRequest {
  @NotBlank
  private String userId;

  @NotBlank
  private String message;
}
