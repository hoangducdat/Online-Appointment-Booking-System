package org.project.backend.appointment.service;

import java.util.concurrent.TimeUnit;

public interface RedisService {
  void save(String key, Object value, long timeout, TimeUnit unit);
  void save(String key, Object value);
  String getValue(String key);
  void deleteKey(String key);
  void blacklistToken(String token, long ttl);
  boolean isTokenBlacklisted(String token);
}
