package org.project.backend.appointment.exception;

public class EmailAlreadyExistsException extends RuntimeException {
  public EmailAlreadyExistsException(String email) {
    super(String.format("Email %s already exists", email));
  }
}
