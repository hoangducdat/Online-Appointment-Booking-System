package org.project.backend.appointment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.backend.appointment.dto.request.AppointmentRequest;
import org.project.backend.appointment.dto.response.AppointmentResponse;
import org.project.backend.appointment.entity.enums.AppointmentStatus;
import org.project.backend.appointment.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {
  private final AppointmentService appointmentService;

  @PostMapping
  @PreAuthorize("hasAnyRole('USER', 'PROVIDER')")
  public ResponseEntity<AppointmentResponse> createAppointment(@Valid @RequestBody AppointmentRequest request) {
    AppointmentResponse response = appointmentService.createAppointment(request);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('USER', 'PROVIDER')")
  public ResponseEntity<List<AppointmentResponse>> getAppointmentsByUser(@RequestParam String userId) {
    List<AppointmentResponse> appointments = appointmentService.getAppointmentsByUser(userId);
    return ResponseEntity.ok(appointments);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('USER', 'PROVIDER')")
  public ResponseEntity<AppointmentResponse> getAppointment(@PathVariable String id) {
    AppointmentResponse response = appointmentService.getAppointment(id);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}/status")
  @PreAuthorize("hasAnyRole('PROVIDER')")
  public ResponseEntity<AppointmentResponse> updateStatus(@PathVariable String id,
      @RequestParam String status) {
    AppointmentResponse response = appointmentService.updateAppointmentStatus(id,
        AppointmentStatus.valueOf(status.toUpperCase()));
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAnyRole('USER', 'PROVIDER')")
  public ResponseEntity<Void> cancelAppointment(@PathVariable String id) {
    appointmentService.cancelAppointment(id);
    return ResponseEntity.noContent().build();
  }
}
