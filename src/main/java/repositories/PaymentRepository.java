package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import models.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
