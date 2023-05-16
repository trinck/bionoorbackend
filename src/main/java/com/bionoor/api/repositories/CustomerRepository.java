package com.bionoor.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bionoor.api.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
