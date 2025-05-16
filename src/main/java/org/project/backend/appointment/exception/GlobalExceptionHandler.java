package org.project.backend.appointment.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ResponseEntity<Map<String, String>> handleEmailAlreadyExists(
      EmailAlreadyExistsException ex) {
    return buildErrorResponse("EMAIL_ALREADY_EXISTS", ex.getMessage(), Objects.requireNonNull(
        ex.getClass().getAnnotation(ResponseStatus.class)).value());
  }

  @ExceptionHandler(UsernameAlreadyExistsException.class)
  public ResponseEntity<Map<String, String>> handleUsernameAlreadyExists(
      UsernameAlreadyExistsException ex) {
    return buildErrorResponse("USERNAME_ALREADY_EXISTS", ex.getMessage(),
        Objects.requireNonNull(ex.getClass().getAnnotation(
            ResponseStatus.class)).value());
  }

  @ExceptionHandler(InvalidPasswordException.class)
  public ResponseEntity<Map<String, String>> handleInvalidPassword(InvalidPasswordException ex) {
    return buildErrorResponse("INVALID_PASSWORD", ex.getMessage(),
        Objects.requireNonNull(ex.getClass().getAnnotation(ResponseStatus.class)).value());
  }

  @ExceptionHandler(InvalidRequestException.class)
  public ResponseEntity<Map<String, String>> handleInvalidPassword(InvalidRequestException ex) {
    return buildErrorResponse("INVALID_PASSWORD", ex.getMessage(),
        Objects.requireNonNull(ex.getClass().getAnnotation(ResponseStatus.class)).value());
  }

  @ExceptionHandler(TimeSlotUnavailableException.class)
  public ResponseEntity<Map<String, String>> handleTimeSlotUnavailable(
      TimeSlotUnavailableException ex) {
    return buildErrorResponse("TIME_SLOT_UNAVAILABLE", ex.getMessage(),
        Objects.requireNonNull(ex.getClass().getAnnotation(ResponseStatus.class)).value());
  }

  @ExceptionHandler(AppointmentAlreadyCancelledException.class)
  public ResponseEntity<Map<String, String>> handleAppointmentAlreadyCancelled(
      AppointmentAlreadyCancelledException ex) {
    return buildErrorResponse("APPOINTMENT_ALREADY_CANCELLED", ex.getMessage(),
        Objects.requireNonNull(ex.getClass().getAnnotation(ResponseStatus.class)).value());
  }

  @ExceptionHandler(IncompleteAppointmentException.class)
  public ResponseEntity<Map<String, String>> handleIncompleteAppointment(
      IncompleteAppointmentException ex) {
    return buildErrorResponse("INCOMPLETE_APPOINTMENT", ex.getMessage(),
        Objects.requireNonNull(ex.getClass().getAnnotation(ResponseStatus.class)).value());
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException ex) {
    return buildErrorResponse("RESOURCE_NOT_FOUND", ex.getMessage(),
        Objects.requireNonNull(ex.getClass().getAnnotation(ResponseStatus.class)).value());
  }

  @ExceptionHandler(InvalidTimeSlotException.class)
  public ResponseEntity<Map<String, String>> handleInvalidTimeSlot(InvalidTimeSlotException ex) {
    return buildErrorResponse("INVALID_TIME_SLOT", ex.getMessage(),
        Objects.requireNonNull(ex.getClass().getAnnotation(ResponseStatus.class)).value());
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<Map<String, String>> handleUnauthorized(UnauthorizedException ex) {
    return buildErrorResponse("UNAUTHORIZED", ex.getMessage(),
        Objects.requireNonNull(ex.getClass().getAnnotation(ResponseStatus.class)).value());
  }

  private ResponseEntity<Map<String, String>> buildErrorResponse(String errorCode, String message,
      HttpStatus status) {
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put("error", errorCode);
    errorResponse.put("message", message);
    return new ResponseEntity<>(errorResponse, status);
  }
}
