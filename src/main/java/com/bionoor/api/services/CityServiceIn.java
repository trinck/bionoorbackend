package com.bionoor.api.services;

import java.util.List;

import com.bionoor.api.dto.InputCityDTO;
import com.bionoor.api.models.City;
public interface CityServiceIn {

	 public City saveCity(City city) ;
	    
	    
	    public City saveCity(InputCityDTO cityDTO) ;

	    public City getCityById(Long id);

	    public List<City> getAllCities() ;

	    public City deleteCity(Long id) ;
}
