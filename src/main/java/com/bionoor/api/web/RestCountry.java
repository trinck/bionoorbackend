package com.bionoor.api.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Consumer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bionoor.api.dto.InputCountryDTO;
import com.bionoor.api.dto.OutputCountry;
import com.bionoor.api.models.Country;
import com.bionoor.api.services.CountryService;

@RestController
@RequestMapping("api/countries")
public class RestCountry {
	@Autowired
	 private  CountryService countryService;

	    
	   
	    @PostMapping(value = "/save")
	    public OutputCountry createCountry(@RequestBody InputCountryDTO countryDTO) {
	    	
	        Country createdCountry = countryService.saveCountry(countryDTO);
	        return new OutputCountry(createdCountry);
	    }

	    @GetMapping("/{id}")
	    public OutputCountry getCountryById(@PathVariable Long id) {
	        Country country = countryService.getCountryById(id);
	        return new OutputCountry(country);
	    }

	    @GetMapping
	    public List<OutputCountry> getAllCountries() {
	        List<Country> countries = countryService.getAllCountries();
	        List<OutputCountry> outputCountries = new ArrayList<>();
	        countries.forEach(country ->{
	        	outputCountries.add(new OutputCountry(country));
	        });
	        
	        return outputCountries;
	    }

	    @DeleteMapping("/{id}")
	    public OutputCountry deleteCountry(@PathVariable Long id) {
	      Country country =  countryService.deleteCountry(id);
	        return new OutputCountry(country);
	    }
	
}
