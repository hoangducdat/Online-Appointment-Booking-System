package org.project.backend.appointment.exception;

public class InvalidTimeSlotException extends RuntimeException {
  public InvalidTimeSlotException(String message) {
    super(message);
  }
}
