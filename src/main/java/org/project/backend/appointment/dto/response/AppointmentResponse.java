package org.project.backend.appointment.dto.response;

import lombok.Data;
import java.time.LocalDateTime;
import org.project.backend.appointment.entity.enums.AppointmentStatus;

@Data
public class AppointmentResponse {
  private String id;
  private String userId;
  private String providerId;
  private String activityId;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private AppointmentStatus status;
  private String notes;
}
