package org.project.backend.appointment.repository;

import org.project.backend.appointment.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, String> {
  List<Review> findByProviderId(String providerId);
}
