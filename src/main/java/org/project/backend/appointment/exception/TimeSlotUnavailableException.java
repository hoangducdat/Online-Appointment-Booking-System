package org.project.backend.appointment.exception;

public class TimeSlotUnavailableException extends RuntimeException {
  public TimeSlotUnavailableException(String message) {
    super(message);
  }
}
