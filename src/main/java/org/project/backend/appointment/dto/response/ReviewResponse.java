package org.project.backend.appointment.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReviewResponse {
  private String id;
  private String userId;
  private String providerId;
  private String appointmentId;
  private int rating;
  private String comment;
  private LocalDateTime createdAt;
}
