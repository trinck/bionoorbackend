package com.bionoor.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bionoor.api.models.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
