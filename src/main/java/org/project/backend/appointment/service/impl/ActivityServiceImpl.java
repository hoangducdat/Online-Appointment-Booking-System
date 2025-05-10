package org.project.backend.appointment.service.impl;

import lombok.RequiredArgsConstructor;
import org.project.backend.appointment.dto.request.ActivityRequest;
import org.project.backend.appointment.dto.response.ActivityResponse;
import org.project.backend.appointment.entity.Activity;
import org.project.backend.appointment.entity.ServiceProvider;
import org.project.backend.appointment.exception.InvalidRequestException;
import org.project.backend.appointment.exception.ResourceNotFoundException;
import org.project.backend.appointment.repository.ActivityRepository;
import org.project.backend.appointment.repository.ServiceProviderRepository;
import org.project.backend.appointment.service.ActivityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
  private final ActivityRepository activityRepository;
  private final ServiceProviderRepository providerRepository;

  @Override
  @Transactional
  public ActivityResponse createActivity(ActivityRequest request) {
    ServiceProvider provider = providerRepository.findById(request.getProviderId())
        .orElseThrow(() -> new ResourceNotFoundException("Provider", request.getProviderId()));

    if (request.getDurationMinutes() <= 0) {
      throw new InvalidRequestException("Duration must be greater than 0");
    }

    Activity activity = new Activity();
    activity.setId(UUID.randomUUID().toString());
    activity.setProvider(provider);
    activity.setName(request.getName());
    activity.setDurationMinutes(request.getDurationMinutes());
    activity.setPrice(request.getPrice());
    activity.setDescription(request.getDescription());

    activity = activityRepository.save(activity);
    return mapToResponse(activity);
  }

  @Override
  public List<ActivityResponse> getActivitiesByProvider(String providerId) {
    return activityRepository.findByProviderId(providerId).stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  @Override
  public ActivityResponse getActivity(String id) {
    Activity activity = activityRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Activity", id));
    return mapToResponse(activity);
  }

  @Override
  @Transactional
  public ActivityResponse updateActivity(String id, ActivityRequest request) {
    Activity activity = activityRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Activity", id));

    if (request.getDurationMinutes() <= 0) {
      throw new InvalidRequestException("Duration must be greater than 0");
    }

    activity.setName(request.getName());
    activity.setDurationMinutes(request.getDurationMinutes());
    activity.setPrice(request.getPrice());
    activity.setDescription(request.getDescription());

    activity = activityRepository.save(activity);
    return mapToResponse(activity);
  }

  @Override
  @Transactional
  public void deleteActivity(String id) {
    Activity activity = activityRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Activity", id));
    activityRepository.delete(activity);
  }

  private ActivityResponse mapToResponse(Activity activity) {
    ActivityResponse response = new ActivityResponse();
    response.setId(activity.getId());
    response.setProviderId(activity.getProvider().getId());
    response.setName(activity.getName());
    response.setDurationMinutes(activity.getDurationMinutes());
    response.setPrice(activity.getPrice());
    response.setDescription(activity.getDescription());
    return response;
  }
}