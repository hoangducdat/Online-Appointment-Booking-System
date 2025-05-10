package org.project.backend.appointment.entity;

import jakarta.persistence.*;

import lombok.Data;

import java.time.LocalTime;
import org.project.backend.appointment.entity.enums.DayOfWeek;

@Entity
@Table(name = "working_hours")
@Data
public class WorkingHours {
  @Id
  private String id;

  @ManyToOne
  @JoinColumn(name = "provider_id", nullable = false)
  private ServiceProvider provider;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private DayOfWeek dayOfWeek;

  @Column(nullable = false)
  private LocalTime startTime;

  @Column(nullable = false)
  private LocalTime endTime;

}
