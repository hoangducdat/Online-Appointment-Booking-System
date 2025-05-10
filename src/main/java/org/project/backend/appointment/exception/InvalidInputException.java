package org.project.backend.appointment.exception;

public class InvalidInputException extends RuntimeException {
  public InvalidInputException(String message) {
    super(message);
  }
}
