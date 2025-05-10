package org.project.backend.appointment.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "activity")
@Data
public class Activity {
  @Id
  private String id;

  @ManyToOne
  @JoinColumn(name = "provider_id", nullable = false)
  private ServiceProvider provider;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private int durationMinutes;

  private double price;
  private String description;
}
