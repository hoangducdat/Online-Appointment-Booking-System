package org.project.backend.appointment.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RegisterRequest {
  @NotBlank
  private String fullName;

  @Email
  @NotBlank(message = "Email is required")
  private String email;

  @NotBlank(message = "Username is required")
  private String username;

  @NotBlank(message = "Password is required")
  private String password;

  @NotBlank(message = "Confirm password is required")
  private String confirmPassword;
  private String role;
  private String phoneNumber;

}
