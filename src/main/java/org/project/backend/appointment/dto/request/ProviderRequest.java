package org.project.backend.appointment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProviderRequest {
  @NotBlank
  private String userId;

  @NotBlank
  private String businessName;

  private String address;
  private String industry;
  private String logoUrl;
}
