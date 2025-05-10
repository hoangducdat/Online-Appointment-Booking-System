package org.project.backend.appointment.service;

import java.util.List;
import org.project.backend.appointment.dto.request.ActivityRequest;
import org.project.backend.appointment.dto.response.ActivityResponse;

public interface ActivityService {
  ActivityResponse createActivity(ActivityRequest request);
  List<ActivityResponse> getActivitiesByProvider(String providerId);
  ActivityResponse getActivity(String id);
  ActivityResponse updateActivity(String id, ActivityRequest request);
  void deleteActivity(String id);
}
