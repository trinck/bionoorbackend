package com.bionoor.api.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bionoor.api.dto.InputCityDTO;
import com.bionoor.api.dto.OutputCityDTO;
import com.bionoor.api.models.City;
import com.bionoor.api.services.CityService;

@RestController
@RequestMapping("api/cities")
public class RestCity {

	
	 @Autowired
	private  CityService cityService;

   
    @PostMapping(value = "/save")
    public OutputCityDTO createCity(@RequestBody InputCityDTO cityDTO) {
       
        City createdCity = cityService.saveCity(cityDTO);
        return new OutputCityDTO(createdCity);
    }

    @GetMapping("/{id}")
    public OutputCityDTO getCityById(@PathVariable Long id) {
        City city = cityService.getCityById(id);
        return new OutputCityDTO(city);
    }

    @GetMapping
    public List<OutputCityDTO> getAllCities() {
        List<City> cities = cityService.getAllCities();
        
        List<OutputCityDTO> cityDTOs = new ArrayList<>();
        cities.forEach(city ->{
        	cityDTOs.add(new OutputCityDTO(city));
        });
        
        
        return cityDTOs;
    }

    @DeleteMapping("/{id}")
    public OutputCityDTO deleteCity(@PathVariable Long id) {
        
    	  City city = this.cityService.deleteCity(id);
        return new OutputCityDTO(city);
    }

	
	
}
