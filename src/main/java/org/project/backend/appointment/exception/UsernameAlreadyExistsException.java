package org.project.backend.appointment.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
  public UsernameAlreadyExistsException(String username) {
    super(String.format("Username %s already exists", username));
  }
}
