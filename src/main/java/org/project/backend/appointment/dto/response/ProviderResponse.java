package org.project.backend.appointment.dto.response;

import lombok.Data;

@Data
public class ProviderResponse {
  private String id;
  private String userId;
  private String businessName;
  private String address;
  private String industry;
  private String logoUrl;
}
