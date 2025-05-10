package org.project.backend.appointment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AppointmentRequest {
  @NotBlank
  private String userId;

  @NotBlank
  private String providerId;

  @NotBlank
  private String activityId;

  @NotNull
  private LocalDateTime startTime;

  private String notes;
}
