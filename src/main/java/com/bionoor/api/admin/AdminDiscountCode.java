package com.bionoor.api.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bionoor.api.admin.AdminCategory.InputCategory;
import com.bionoor.api.models.Category;
import com.bionoor.api.models.DiscountCode;
import com.bionoor.api.services.CategoryService;
import com.bionoor.api.services.DiscountCodeService;

import lombok.Data;
import lombok.NoArgsConstructor;

@Controller
public class AdminDiscountCode {

	
	@Autowired
	DiscountCodeService discountCodeService;
	
	
	
	
	@Value("${app.name}")
	private String name;
	@Value("${app.logo}")
	 private String logo;
	
	
	

	@GetMapping(value = "/discounts")
	public String discounts(Model model) {
		 List<DiscountCode> discountCodes = this.discountCodeService.all();
		model.addAttribute("name", name);
		model.addAttribute("discountCodes", discountCodes);
		//System.out.println(categories);
		return "";
	}
	
	@GetMapping(value = "/discountForm")
	public String discountForm(Model model) {
		DiscountCode discountCode = new DiscountCode();
		model.addAttribute("name", name);
		model.addAttribute("discountCode", discountCode);
		
		return "";
	}
	
	
	@GetMapping(value = "/discounts/{id}")
	public String discount(Model model, @PathVariable(value = "id") Long id) {
		
		DiscountCode discountCode = this.discountCodeService.getById(id);	
		model.addAttribute("discountCode", discountCode);
	
		model.addAttribute("name", name);
		return "";
	}
	
	
	@PostMapping(value = "/discounts/add")
	public String addDiscount(Model model,@ModelAttribute DiscountCode DiscountCode) {
		
		this.discountCodeService.add(DiscountCode);
		
	    return ""; 
		
	}
	
	
	
	
	
	
}
