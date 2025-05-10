package org.project.backend.appointment.constant;

public class Constant {
  private Constant() {}
  public static final String TOKEN_TYPE = "Bearer";
  public static final long ACCESS_TOKEN_TTL_SECONDS = 86400; // 1 day
  public static final long REFRESH_TOKEN_TTL_SECONDS = 604800;
}
