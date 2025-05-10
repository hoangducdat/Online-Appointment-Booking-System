package org.project.backend.appointment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.backend.appointment.dto.request.ReviewRequest;
import org.project.backend.appointment.dto.response.ReviewResponse;
import org.project.backend.appointment.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
  private final ReviewService reviewService;

  @PostMapping
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<ReviewResponse> createReview(@Valid @RequestBody ReviewRequest request) {
    ReviewResponse response = reviewService.createReview(request);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping("/provider/{providerId}")
  public ResponseEntity<List<ReviewResponse>> getReviewsByProvider(@PathVariable String providerId) {
    List<ReviewResponse> reviews = reviewService.getReviewsByProvider(providerId);
    return ResponseEntity.ok(reviews);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<ReviewResponse> updateReview(@PathVariable String id,
      @Valid @RequestBody ReviewRequest request) {
    ReviewResponse response = reviewService.updateReview(id, request);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<Void> deleteReview(@PathVariable String id) {
    reviewService.deleteReview(id);
    return ResponseEntity.noContent().build();
  }
}