package org.project.backend.appointment.entity;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Table(name = "service_providers")
@Data
public class ServiceProvider {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @OneToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false)
  private String businessName;

  private String address;
  private String industry;
  private String logoUrl;
}
