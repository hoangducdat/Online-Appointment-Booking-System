package org.project.backend.appointment.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import org.project.backend.appointment.entity.enums.AppointmentStatus;

@Entity
@Table(name = "appointments")
@Data
public class Appointment {
  @Id
  private String id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "provider_id", nullable = false)
  private ServiceProvider provider;

  @ManyToOne
  @JoinColumn(name = "activity_id", nullable = false)
  private Activity activity;

  @Column(nullable = false)
  private LocalDateTime startTime;

  @Column(nullable = false)
  private LocalDateTime endTime;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private AppointmentStatus status;

  private String notes;
}
