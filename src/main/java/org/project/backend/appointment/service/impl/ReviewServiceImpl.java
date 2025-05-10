package org.project.backend.appointment.service.impl;

import lombok.RequiredArgsConstructor;
import org.project.backend.appointment.dto.request.ReviewRequest;
import org.project.backend.appointment.dto.response.ReviewResponse;
import org.project.backend.appointment.entity.Appointment;
import org.project.backend.appointment.entity.Review;
import org.project.backend.appointment.entity.ServiceProvider;
import org.project.backend.appointment.entity.User;
import org.project.backend.appointment.entity.enums.AppointmentStatus;
import org.project.backend.appointment.exception.IncompleteAppointmentException;
import org.project.backend.appointment.exception.ResourceNotFoundException;
import org.project.backend.appointment.repository.AppointmentRepository;
import org.project.backend.appointment.repository.ReviewRepository;
import org.project.backend.appointment.repository.ServiceProviderRepository;
import org.project.backend.appointment.repository.UserRepository;
import org.project.backend.appointment.service.ReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
  private final ReviewRepository reviewRepository;
  private final UserRepository userRepository;
  private final ServiceProviderRepository providerRepository;
  private final AppointmentRepository appointmentRepository;

  @Override
  @Transactional
  public ReviewResponse createReview(ReviewRequest request) {
    User user = userRepository.findById(request.getUserId())
        .orElseThrow(() -> new ResourceNotFoundException("User", request.getUserId()));

    ServiceProvider provider = providerRepository.findById(request.getProviderId())
        .orElseThrow(() -> new ResourceNotFoundException("Provider", request.getProviderId()));

    Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
        .orElseThrow(() -> new ResourceNotFoundException("Appointment", request.getAppointmentId()));

    if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
      throw new IncompleteAppointmentException(request.getAppointmentId());
    }

    Review review = new Review();
    review.setId(UUID.randomUUID().toString());
    review.setUser(user);
    review.setProvider(provider);
    review.setAppointment(appointment);
    review.setRating(request.getRating());
    review.setComment(request.getComment());

    review = reviewRepository.save(review);
    return mapToResponse(review);
  }

  @Override
  public List<ReviewResponse> getReviewsByProvider(String providerId) {
    return reviewRepository.findByProviderId(providerId).stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public ReviewResponse updateReview(String id, ReviewRequest request) {
    Review review = reviewRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Review", id));

    review.setRating(request.getRating());
    review.setComment(request.getComment());

    review = reviewRepository.save(review);
    return mapToResponse(review);
  }

  @Override
  @Transactional
  public void deleteReview(String id) {
    Review review = reviewRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Review", id));
    reviewRepository.delete(review);
  }

  private ReviewResponse mapToResponse(Review review) {
    ReviewResponse response = new ReviewResponse();
    response.setId(review.getId());
    response.setUserId(review.getUser().getId());
    response.setProviderId(review.getProvider().getId());
    response.setAppointmentId(review.getAppointment().getId());
    response.setRating(review.getRating());
    response.setComment(review.getComment());
    response.setCreatedAt(review.getCreatedAt());
    return response;
  }
}

