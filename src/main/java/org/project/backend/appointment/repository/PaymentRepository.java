package org.project.backend.appointment.repository;

import org.project.backend.appointment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}
