package com.bionoor.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bionoor.api.models.City;
import com.bionoor.api.models.Product;

public interface CityRepository extends JpaRepository<City, Long>{

}
