package org.project.backend.appointment.exception;

public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String resource, String id) {
    super(String.format("%s with id %s not found", resource, id));
  }
}
