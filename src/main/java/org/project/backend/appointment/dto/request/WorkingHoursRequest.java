package org.project.backend.appointment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class WorkingHoursRequest {
  @NotBlank
  private String providerId;

  @NotBlank
  private String dayOfWeek;

  @NotNull
  private LocalTime startTime;

  @NotNull
  private LocalTime endTime;
}
