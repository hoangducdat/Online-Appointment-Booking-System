package org.project.backend.appointment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.backend.appointment.dto.request.ActivityRequest;
import org.project.backend.appointment.dto.response.ActivityResponse;
import org.project.backend.appointment.service.ActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity")
@RequiredArgsConstructor
public class ActivityController {
  private final ActivityService activityService;

  @PostMapping
  @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
  public ResponseEntity<ActivityResponse> createActivity(@Valid @RequestBody ActivityRequest request) {
    ActivityResponse response = activityService.createActivity(request);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<ActivityResponse>> getActivitiesByProvider(@RequestParam String providerId) {
    List<ActivityResponse> activities = activityService.getActivitiesByProvider(providerId);
    return ResponseEntity.ok(activities);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ActivityResponse> getActivity(@PathVariable String id) {
    ActivityResponse response = activityService.getActivity(id);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
  public ResponseEntity<ActivityResponse> updateActivity(@PathVariable String id,
      @Valid @RequestBody ActivityRequest request) {
    ActivityResponse response = activityService.updateActivity(id, request);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
  public ResponseEntity<Void> deleteActivity(@PathVariable String id) {
    activityService.deleteActivity(id);
    return ResponseEntity.noContent().build();
  }
}
