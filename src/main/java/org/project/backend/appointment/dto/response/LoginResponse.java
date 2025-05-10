package org.project.backend.appointment.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LoginResponse {

  private String accessToken;
  private String refreshToken;
  private long accessTokenTtl;
  private long refreshTokenTtl;
  private String tokenType;

  public LoginResponse() {
  }

  public LoginResponse(String accessToken, String refreshToken, long accessTokenTtl,
      long refreshTokenTtl,
      String tokenType) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.accessTokenTtl = accessTokenTtl;
    this.refreshTokenTtl = refreshTokenTtl;
    this.tokenType = tokenType;
  }
}
