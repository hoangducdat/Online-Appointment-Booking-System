package org.project.backend.appointment.service;

import java.util.List;
import org.project.backend.appointment.dto.request.ReviewRequest;
import org.project.backend.appointment.dto.response.ReviewResponse;

public interface ReviewService {
  ReviewResponse createReview(ReviewRequest request);
  List<ReviewResponse> getReviewsByProvider(String providerId);
  ReviewResponse updateReview(String id, ReviewRequest request);
  void deleteReview(String id);
}
