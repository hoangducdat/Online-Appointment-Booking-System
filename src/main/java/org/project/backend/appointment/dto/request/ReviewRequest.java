package org.project.backend.appointment.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReviewRequest {
  @NotBlank
  private String userId;

  @NotBlank
  private String providerId;

  @NotBlank
  private String appointmentId;

  @Min(1)
  @Max(5)
  private int rating;

  private String comment;
}
