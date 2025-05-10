package org.project.backend.appointment.service.impl;

import lombok.RequiredArgsConstructor;
import org.project.backend.appointment.dto.request.ProviderRequest;
import org.project.backend.appointment.dto.request.WorkingHoursRequest;
import org.project.backend.appointment.dto.response.ProviderResponse;
import org.project.backend.appointment.dto.response.WorkingHoursResponse;
import org.project.backend.appointment.entity.ServiceProvider;
import org.project.backend.appointment.entity.User;
import org.project.backend.appointment.entity.WorkingHours;
import org.project.backend.appointment.entity.enums.DayOfWeek;
import org.project.backend.appointment.exception.InvalidTimeSlotException;
import org.project.backend.appointment.exception.ResourceNotFoundException;
import org.project.backend.appointment.repository.ServiceProviderRepository;
import org.project.backend.appointment.repository.UserRepository;
import org.project.backend.appointment.repository.WorkingHoursRepository;
import org.project.backend.appointment.service.ProviderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {
  private final ServiceProviderRepository providerRepository;
  private final UserRepository userRepository;
  private final WorkingHoursRepository workingHoursRepository;

  @Override
  @Transactional
  public ProviderResponse createProvider(ProviderRequest request) {
    User user = userRepository.findById(request.getUserId())
        .orElseThrow(() -> new ResourceNotFoundException("User", request.getUserId()));

    ServiceProvider provider = new ServiceProvider();
    provider.setId(UUID.randomUUID().toString());
    provider.setUser(user);
    provider.setBusinessName(request.getBusinessName());
    provider.setAddress(request.getAddress());
    provider.setIndustry(request.getIndustry());
    provider.setLogoUrl(request.getLogoUrl());

    provider = providerRepository.save(provider);
    return mapToResponse(provider);
  }

  @Override
  public List<ProviderResponse> getAllProviders() {
    return providerRepository.findAll().stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  @Override
  public ProviderResponse getProvider(String id) {
    ServiceProvider provider = providerRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Provider", id));
    return mapToResponse(provider);
  }

  @Override
  @Transactional
  public ProviderResponse updateProvider(String id, ProviderRequest request) {
    ServiceProvider provider = providerRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Provider", id));

    provider.setBusinessName(request.getBusinessName());
    provider.setAddress(request.getAddress());
    provider.setIndustry(request.getIndustry());
    provider.setLogoUrl(request.getLogoUrl());

    provider = providerRepository.save(provider);
    return mapToResponse(provider);
  }

  @Override
  @Transactional
  public WorkingHoursResponse createWorkingHours(WorkingHoursRequest request) {
    ServiceProvider provider = providerRepository.findById(request.getProviderId())
        .orElseThrow(() -> new ResourceNotFoundException("Provider", request.getProviderId()));

    if (request.getStartTime().isAfter(request.getEndTime())) {
      throw new InvalidTimeSlotException("Start time must be before end time");
    }

    WorkingHours workingHours = new WorkingHours();
    workingHours.setId(UUID.randomUUID().toString());
    workingHours.setProvider(provider);
    workingHours.setDayOfWeek(DayOfWeek.valueOf(request.getDayOfWeek()));
    workingHours.setStartTime(request.getStartTime());
    workingHours.setEndTime(request.getEndTime());

    workingHours = workingHoursRepository.save(workingHours);
    return mapToWorkingHoursResponse(workingHours);
  }

  private ProviderResponse mapToResponse(ServiceProvider provider) {
    ProviderResponse response = new ProviderResponse();
    response.setId(provider.getId());
    response.setUserId(provider.getUser().getId());
    response.setBusinessName(provider.getBusinessName());
    response.setAddress(provider.getAddress());
    response.setIndustry(provider.getIndustry());
    response.setLogoUrl(provider.getLogoUrl());
    return response;
  }

  private WorkingHoursResponse mapToWorkingHoursResponse(WorkingHours workingHours) {
    WorkingHoursResponse response = new WorkingHoursResponse();
    response.setId(workingHours.getId());
    response.setProviderId(workingHours.getProvider().getId());
    response.setDayOfWeek(workingHours.getDayOfWeek().name());
    response.setStartTime(workingHours.getStartTime());
    response.setEndTime(workingHours.getEndTime());
    return response;
  }
}
