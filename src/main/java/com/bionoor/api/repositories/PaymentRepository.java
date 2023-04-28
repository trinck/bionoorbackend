package com.bionoor.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bionoor.api.models.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
