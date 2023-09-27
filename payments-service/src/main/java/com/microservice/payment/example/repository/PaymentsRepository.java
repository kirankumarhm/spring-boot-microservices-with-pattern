package com.microservice.payment.example.repository;

import com.microservice.payment.example.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsRepository extends JpaRepository<Payment, String> {
}
