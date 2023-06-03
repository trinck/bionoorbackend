package com.bionoor.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bionoor.api.models.Country;

public interface CountryRepsitory extends JpaRepository<Country, Long>{

}
