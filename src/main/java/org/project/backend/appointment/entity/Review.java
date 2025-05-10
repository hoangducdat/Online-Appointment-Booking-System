package org.project.backend.appointment.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Data
public class Review {
  @Id
  private String id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "provider_id", nullable = false)
  private ServiceProvider provider;

  @OneToOne
  @JoinColumn(name = "appointment_id", nullable = false)
  private Appointment appointment;

  @Column(nullable = false)
  private int rating;

  private String comment;

  private LocalDateTime createdAt;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
  }
}
