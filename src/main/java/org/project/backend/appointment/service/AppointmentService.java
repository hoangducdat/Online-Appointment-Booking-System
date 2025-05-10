package org.project.backend.appointment.service;

import java.util.List;
import org.project.backend.appointment.dto.request.AppointmentRequest;
import org.project.backend.appointment.dto.response.AppointmentResponse;
import org.project.backend.appointment.entity.enums.AppointmentStatus;

public interface AppointmentService {
  AppointmentResponse createAppointment(AppointmentRequest request);
  List<AppointmentResponse> getAppointmentsByUser(String userId);
  AppointmentResponse getAppointment(String id);
  AppointmentResponse updateAppointmentStatus(String id, AppointmentStatus status);
  void cancelAppointment(String id);
}
