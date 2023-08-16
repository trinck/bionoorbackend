package com.bionoor.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bionoor.api.dto.InputCountryDTO;
import com.bionoor.api.models.City;
import com.bionoor.api.models.Country;
import com.bionoor.api.repositories.CountryRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CountryService implements CountryServiceIn{

	
	  @Autowired
    private  CountryRepository countryRepository;

  

    
  
    public Country saveCountry(Country country) {
        return countryRepository.save(country);
    }

    
    public Country saveCountry(InputCountryDTO country) {
    	Country country2 = new Country(country);
    	
    	country.getCities().forEach(city -> {
    		City city2 = new City(city);
    		city2.setCountry(country2);
    		country2.getCities().add(city2);
    	});
    	
        return countryRepository.save(country2);
    }

    
    
    public Country getCountryById(Long id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Country not found with id: " + id));
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Country deleteCountry(Long id) {
    	Country country = this.getCountryById(id);
        countryRepository.deleteById(id);
        
        return country;
    }

    // Add additional methods as needed
}
