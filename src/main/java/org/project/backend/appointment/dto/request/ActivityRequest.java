package org.project.backend.appointment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ActivityRequest {
  @NotBlank
  private String providerId;

  @NotBlank
  private String name;

  @NotNull
  private Integer durationMinutes;

  private Double price;
  private String description;
}
