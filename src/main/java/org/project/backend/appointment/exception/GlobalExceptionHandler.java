package org.project.backend.appointment.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.project.backend.appointment.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        Map<String, String> details = new HashMap<>();
        details.put("error", "EMAIL_ALREADY_EXISTS");
        details.put("details", ex.getMessage());
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleUsernameAlreadyExists(UsernameAlreadyExistsException ex) {
        Map<String, String> details = new HashMap<>();
        details.put("error", "USERNAME_ALREADY_EXISTS");
        details.put("details", ex.getMessage());
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleInvalidPassword(InvalidPasswordException ex) {
        Map<String, String> details = new HashMap<>();
        details.put("error", "INVALID_PASSWORD");
        details.put("details", ex.getMessage());
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleInvalidRequest(InvalidRequestException ex) {
        Map<String, String> details = new HashMap<>();
        details.put("error", "INVALID_REQUEST");
        details.put("details", ex.getMessage());
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TimeSlotUnavailableException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleTimeSlotUnavailable(TimeSlotUnavailableException ex) {
        Map<String, String> details = new HashMap<>();
        details.put("error", "TIME_SLOT_UNAVAILABLE");
        details.put("details", ex.getMessage());
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AppointmentAlreadyCancelledException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleAppointmentAlreadyCancelled(AppointmentAlreadyCancelledException ex) {
        Map<String, String> details = new HashMap<>();
        details.put("error", "APPOINTMENT_ALREADY_CANCELLED");
        details.put("details", ex.getMessage());
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IncompleteAppointmentException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleIncompleteAppointment(IncompleteAppointmentException ex) {
        Map<String, String> details = new HashMap<>();
        details.put("error", "INCOMPLETE_APPOINTMENT");
        details.put("details", ex.getMessage());
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, String> details = new HashMap<>();
        details.put("error", "RESOURCE_NOT_FOUND");
        details.put("details", ex.getMessage());
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidTimeSlotException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleInvalidTimeSlot(InvalidTimeSlotException ex) {
        Map<String, String> details = new HashMap<>();
        details.put("error", "INVALID_TIME_SLOT");
        details.put("details", ex.getMessage());
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleUnauthorized(UnauthorizedException ex) {
        Map<String, String> details = new HashMap<>();
        details.put("error", "UNAUTHORIZED");
        details.put("details", ex.getMessage());
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}