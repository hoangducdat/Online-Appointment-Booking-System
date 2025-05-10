package org.project.backend.appointment.service;

import java.util.List;
import org.project.backend.appointment.dto.request.ProviderRequest;
import org.project.backend.appointment.dto.request.WorkingHoursRequest;
import org.project.backend.appointment.dto.response.ProviderResponse;
import org.project.backend.appointment.dto.response.WorkingHoursResponse;

public interface ProviderService {
  ProviderResponse createProvider(ProviderRequest request);
  List<ProviderResponse> getAllProviders();
  ProviderResponse getProvider(String id);
  ProviderResponse updateProvider(String id, ProviderRequest request);
  WorkingHoursResponse createWorkingHours(WorkingHoursRequest request);
}
