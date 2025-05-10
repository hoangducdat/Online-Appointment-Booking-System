package org.project.backend.appointment.exception;

public class AppointmentAlreadyCancelledException extends RuntimeException {
  public AppointmentAlreadyCancelledException(String appointmentId) {
    super(String.format("Appointment with id %s is already cancelled", appointmentId));
  }
}
