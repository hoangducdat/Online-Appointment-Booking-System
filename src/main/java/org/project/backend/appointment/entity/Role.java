package org.project.backend.appointment.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role {
  @Id
  private String id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private RoleName name;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  public enum RoleName {
    ADMIN, PROVIDER, USER
  }
}
