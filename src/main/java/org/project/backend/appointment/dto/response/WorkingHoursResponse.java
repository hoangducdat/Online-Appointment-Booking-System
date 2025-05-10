package org.project.backend.appointment.dto.response;

import lombok.Data;
import java.time.LocalTime;

@Data
public class WorkingHoursResponse {
  private String id;
  private String providerId;
  private String dayOfWeek;
  private LocalTime startTime;
  private LocalTime endTime;
}