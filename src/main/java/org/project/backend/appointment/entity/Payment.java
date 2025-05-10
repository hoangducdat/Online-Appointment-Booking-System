package org.project.backend.appointment.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import org.project.backend.appointment.entity.enums.PaymentStatus;

@Entity
@Table(name = "payments")
@Data
public class Payment {
  @Id
  private String id;

  @OneToOne
  @JoinColumn(name = "appointment_id", nullable = false)
  private Appointment appointment;

  @Column(nullable = false)
  private double amount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PaymentStatus status;

  private String paymentMethod;
  private String transactionId;

  private LocalDateTime createdAt;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
  }

}
