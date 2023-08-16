package com.bionoor.api.services;

import java.util.List;

import com.bionoor.api.dto.InputCountryDTO;
import com.bionoor.api.models.Country;

public interface CountryServiceIn {

	
    public Country saveCountry(Country country) ;
    public Country saveCountry(InputCountryDTO country);

    
    
    public Country getCountryById(Long id);
    public List<Country> getAllCountries();

    public Country deleteCountry(Long id) ;

}
