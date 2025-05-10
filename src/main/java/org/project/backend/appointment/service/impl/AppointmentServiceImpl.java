package org.project.backend.appointment.service.impl;

import lombok.RequiredArgsConstructor;
import org.project.backend.appointment.dto.request.AppointmentRequest;
import org.project.backend.appointment.dto.response.AppointmentResponse;
import org.project.backend.appointment.entity.Activity;
import org.project.backend.appointment.entity.Appointment;
import org.project.backend.appointment.entity.ServiceProvider;
import org.project.backend.appointment.entity.User;
import org.project.backend.appointment.entity.WorkingHours;
import org.project.backend.appointment.entity.enums.AppointmentStatus;
import org.project.backend.appointment.entity.enums.DayOfWeek;
import org.project.backend.appointment.exception.AppointmentAlreadyCancelledException;
import org.project.backend.appointment.exception.InvalidTimeSlotException;
import org.project.backend.appointment.exception.ResourceNotFoundException;
import org.project.backend.appointment.exception.TimeSlotUnavailableException;
import org.project.backend.appointment.repository.ActivityRepository;
import org.project.backend.appointment.repository.AppointmentRepository;
import org.project.backend.appointment.repository.ServiceProviderRepository;
import org.project.backend.appointment.repository.UserRepository;
import org.project.backend.appointment.repository.WorkingHoursRepository;
import org.project.backend.appointment.service.AppointmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
  private final AppointmentRepository appointmentRepository;
  private final UserRepository userRepository;
  private final ServiceProviderRepository providerRepository;
  private final ActivityRepository activityRepository;
  private final WorkingHoursRepository workingHoursRepository;

  @Override
  @Transactional
  public AppointmentResponse createAppointment(AppointmentRequest request) {
    // Validate user
    User user = userRepository.findById(request.getUserId())
        .orElseThrow(() -> new ResourceNotFoundException("User", request.getUserId()));

    // Validate provider
    ServiceProvider provider = providerRepository.findById(request.getProviderId())
        .orElseThrow(() -> new ResourceNotFoundException("Provider", request.getProviderId()));

    // Validate activity
    Activity activity = activityRepository.findById(request.getActivityId())
        .orElseThrow(() -> new ResourceNotFoundException("Activity", request.getActivityId()));

    // Validate time slot
    LocalDateTime startTime = request.getStartTime();
    LocalDateTime endTime = startTime.plusMinutes(activity.getDurationMinutes());
    if (startTime.isBefore(LocalDateTime.now())) {
      throw new InvalidTimeSlotException("Cannot schedule appointment in the past");
    }
    if (!isTimeSlotAvailable(provider.getId(), startTime, endTime)) {
      throw new TimeSlotUnavailableException("TimeSlot is unavailable");
    }

    // Create appointment
    Appointment appointment = new Appointment();
    appointment.setId(UUID.randomUUID().toString());
    appointment.setUser(user);
    appointment.setProvider(provider);
    appointment.setActivity(activity);
    appointment.setStartTime(startTime);
    appointment.setEndTime(endTime);
    appointment.setStatus(AppointmentStatus.PENDING);
    appointment.setNotes(request.getNotes());

    appointment = appointmentRepository.save(appointment);
    return mapToResponse(appointment);
  }

  @Override
  public List<AppointmentResponse> getAppointmentsByUser(String userId) {
    return appointmentRepository.findByUserId(userId).stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  @Override
  public AppointmentResponse getAppointment(String id) {
    Appointment appointment = appointmentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Appointment", id));
    return mapToResponse(appointment);
  }

  @Override
  @Transactional
  public AppointmentResponse updateAppointmentStatus(String id, AppointmentStatus status) {
    Appointment appointment = appointmentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Appointment", id));
    appointment.setStatus(status);
    appointment = appointmentRepository.save(appointment);
    return mapToResponse(appointment);
  }

  @Override
  @Transactional
  public void cancelAppointment(String id) {
    Appointment appointment = appointmentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Appointment", id));
    if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
      throw new AppointmentAlreadyCancelledException(id);
    }
    appointment.setStatus(AppointmentStatus.CANCELLED);
    appointmentRepository.save(appointment);
  }

  private boolean isTimeSlotAvailable(String providerId, LocalDateTime startTime, LocalDateTime endTime) {
    LocalDateTime dateTime = LocalDateTime.now();
    DayOfWeek dayOfWeek = DayOfWeek.valueOf(dateTime.getDayOfWeek().name());

    List<WorkingHours> workingHours = workingHoursRepository.findByProviderIdAndDayOfWeek(providerId, dayOfWeek);
    boolean isWithinWorkingHours = workingHours.stream().anyMatch(wh ->
        startTime.toLocalTime().isAfter(wh.getStartTime()) &&
            endTime.toLocalTime().isBefore(wh.getEndTime()));

    if (!isWithinWorkingHours) {
      throw new InvalidTimeSlotException("Requested time is outside provider's working hours");
    }

    // Check existing appointments
    List<Appointment> conflictingAppointments = appointmentRepository.findByProviderIdAndStartTimeBetween(
        providerId, startTime.minusMinutes(1), endTime.plusMinutes(1));
    return conflictingAppointments.isEmpty();
  }

  private AppointmentResponse mapToResponse(Appointment appointment) {
    AppointmentResponse response = new AppointmentResponse();
    response.setId(appointment.getId());
    response.setUserId(appointment.getUser().getId());
    response.setProviderId(appointment.getProvider().getId());
    response.setActivityId(appointment.getActivity().getId());
    response.setStartTime(appointment.getStartTime());
    response.setEndTime(appointment.getEndTime());
    response.setStatus(appointment.getStatus());
    response.setNotes(appointment.getNotes());
    return response;
  }
}
