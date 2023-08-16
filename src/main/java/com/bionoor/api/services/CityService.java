package com.bionoor.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bionoor.api.dto.InputCityDTO;
import com.bionoor.api.models.City;
import com.bionoor.api.models.Country;
import com.bionoor.api.repositories.CityRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CityService implements CityServiceIn{

	@Autowired
    private  CityRepository cityRepository;
	
	@Autowired
	private CountryServiceIn countryService;
    
   

    public City saveCity(City city) {
        return cityRepository.save(city);
    }
    
    
    public City saveCity(InputCityDTO cityDTO) {
        
    	Country country = this.countryService.getCountryById(cityDTO.getCountry());
    	City city = new City(cityDTO);
    	city.setCountry(country);
    	return cityRepository.save(city);
    }

    public City getCityById(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("City not found with id: " + id));
    }

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public City deleteCity(Long id) {
    	
    	City city = this.getCityById(id);
     
        cityRepository.deleteById(id);
        
        return city;
    }

    // Add additional methods as needed
}
