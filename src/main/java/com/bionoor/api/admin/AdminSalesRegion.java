package com.bionoor.api.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bionoor.api.models.Country;
import com.bionoor.api.services.CountryServiceIn;

@Controller
public class AdminSalesRegion {
	
	@Autowired
	private CountryServiceIn countryService;
	
	
	
	@GetMapping("/countries")
	public String regions(Model model) {
		
		
		List<Country> countries = this.countryService.getAllCountries();
		
		model.addAttribute("countries", countries);
		
		return "countries/countries";
	}
	
	
	
	
	
	
	
	

}
