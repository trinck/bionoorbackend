package com.bionoor.api.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminInvoice {

	
	
	@Value("${app.name}")
	private String name;
	@Value("${app.logo}")
	 private String logo;
	
	
	@GetMapping(value = "/invoices")
	/* this id is for existing invoice*/
	public String invoices(Model model) 
	{	model.addAttribute("logo", logo);
		model.addAttribute("name", name);
		
		return "invoices/invoices";
	}
	
	@GetMapping(value = "invoice/edit")
	/* this id is for existing invoice*/
	public String editInvoice(Model model, @RequestParam(name = "id") int id) 
	{
		model.addAttribute("logo", logo);
		model.addAttribute("name", name);
		
		return "invoices/invoiceedit";
	}
	
	@RequestMapping(value = "/invoice", method = RequestMethod.GET)
	/* this id is for invoice*/
	public String formInvoice(Model model, @RequestParam(name = "id") int id) 
	{
		model.addAttribute("logo", logo);
		model.addAttribute("name", name);
		
		return "invoices/invoiceview.html";
	}
}
