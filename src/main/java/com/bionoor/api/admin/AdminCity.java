package com.bionoor.api.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.bionoor.api.models.Country;
import com.bionoor.api.services.CityServiceIn;
import com.bionoor.api.services.CountryServiceIn;

@Controller
public class AdminCity {

	@Autowired
	private CityServiceIn cityServiceIn;
	@Autowired
	private CountryServiceIn countryServiceIn;
	@GetMapping("/countryCities")
	public String cities(Model model,@RequestParam Long countryId) {
		
		Country country = this.countryServiceIn.getCountryById(countryId);
		model.addAttribute("country", country);
		
		return "cities/cities";
	}
}
