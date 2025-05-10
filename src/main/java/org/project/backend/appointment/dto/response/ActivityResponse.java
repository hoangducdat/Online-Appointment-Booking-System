package org.project.backend.appointment.dto.response;

import lombok.Data;

@Data
public class ActivityResponse {
  private String id;
  private String providerId;
  private String name;
  private int durationMinutes;
  private Double price;
  private String description;
}
