package org.project.backend.appointment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.backend.appointment.dto.request.ProviderRequest;
import org.project.backend.appointment.dto.request.WorkingHoursRequest;
import org.project.backend.appointment.dto.response.ProviderResponse;
import org.project.backend.appointment.dto.response.WorkingHoursResponse;
import org.project.backend.appointment.service.ProviderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/providers")
@RequiredArgsConstructor
public class ProviderController {
  private final ProviderService providerService;

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ProviderResponse> createProvider(@Valid @RequestBody ProviderRequest request) {
    ProviderResponse response = providerService.createProvider(request);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<ProviderResponse>> getAllProviders() {
    List<ProviderResponse> providers = providerService.getAllProviders();
    return ResponseEntity.ok(providers);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
  public ResponseEntity<ProviderResponse> getProvider(@PathVariable String id) {
    ProviderResponse response = providerService.getProvider(id);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
  public ResponseEntity<ProviderResponse> updateProvider(@PathVariable String id,
      @Valid @RequestBody ProviderRequest request) {
    ProviderResponse response = providerService.updateProvider(id, request);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/{id}/working-hours")
  @PreAuthorize("hasAnyRole('ADMIN', 'PROVIDER')")
  public ResponseEntity<WorkingHoursResponse> createWorkingHours(@PathVariable String id,
      @Valid @RequestBody WorkingHoursRequest request) {
    request.setProviderId(id);
    WorkingHoursResponse response = providerService.createWorkingHours(request);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}
