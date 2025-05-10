package org.project.backend.appointment.exception;

public class IncompleteAppointmentException extends RuntimeException {
  public IncompleteAppointmentException(String appointmentId) {
    super(String.format("Cannot review appointment with id %s as it is not completed", appointmentId));
  }
}
