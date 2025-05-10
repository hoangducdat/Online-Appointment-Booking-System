package org.project.backend.appointment.service.impl;

import java.util.concurrent.TimeUnit;
import org.project.backend.appointment.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl implements RedisService {

  private final RedisTemplate<String, Object> redisTemplate;

  public RedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @Override
  public void save(String key, Object value, long timeout, TimeUnit unit) {
    redisTemplate.opsForValue().set(key, value, timeout, unit);
  }

  @Override
  public void save(String key, Object value) {
    redisTemplate.opsForValue().set(key, value);
  }

  @Override
  public String getValue(String key) {
    Object value = redisTemplate.opsForValue().get(key);
    return value != null ? value.toString() : null;
  }

  @Override
  public void deleteKey(String key) {
    redisTemplate.delete(key);
  }

  @Override
  public void blacklistToken(String token, long ttl) {
    redisTemplate.opsForValue().set("blacklist:" + token, "blacklisted", ttl, TimeUnit.MILLISECONDS);
  }

  @Override
  public boolean isTokenBlacklisted(String token) {
    return redisTemplate.hasKey("blacklist:" + token);
  }
}
