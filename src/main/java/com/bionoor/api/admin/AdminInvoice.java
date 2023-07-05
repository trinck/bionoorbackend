package com.bionoor.api.admin;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bionoor.api.dto.InputOrderInvoiceDTO;
import com.bionoor.api.models.Customer;
import com.bionoor.api.models.Invoice;
import com.bionoor.api.models.Order;
import com.bionoor.api.models.Payment;
import com.bionoor.api.models.Product;
import com.bionoor.api.models.User;
import com.bionoor.api.services.InvoiceService;
import com.bionoor.api.services.OrderService;
import com.bionoor.api.services.ProductService;
import com.bionoor.api.utils.InvoiceProcessingIn;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Controller
public class AdminInvoice {

	
	
	@Value("${app.name}")
	private String name;
	@Value("${app.logo}")
	 private String logo;
	
	@Autowired
	private InvoiceService invoiceService;
	
	
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping(value = "/invoices")
	/* this id is for existing invoice*/
	public String invoices(Model model) 
	{
		List<Invoice> invoices = this.invoiceService.allInvoices();
		model.addAttribute("invoices", invoices);
		model.addAttribute("logo", logo);
		model.addAttribute("name", name);
		
		return "invoices/invoices";
	}
	
	
	
	
	@GetMapping(value = "/invoicesPages")
	/* this id is for existing invoice*/
	public String page(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,@RequestParam(required = false) Long mc,@RequestParam(defaultValue = "id:ascending") String sort) 
	{
		Page<Invoice> invoices = this.invoiceService.pages(page, size, mc, sort);
		model.addAttribute("invoices", invoices);
		model.addAttribute("logo", logo);
		model.addAttribute("name", name);
		model.addAttribute("totalElements", invoices.getTotalElements());
		model.addAttribute("pages", new int[invoices.getTotalPages()]);
		model.addAttribute("mc", mc);
		model.addAttribute("page", page);
		model.addAttribute("size", size);
		model.addAttribute("totalPages", invoices.getTotalPages());
		model.addAttribute("invoices", invoices.getContent());	
		return "invoices/invoices";
	}
	
	
	
	
	
	@GetMapping(value = "/editInvoice")
	/* this id is for existing invoice*/
	public String editInvoice(Model model, @RequestParam(name = "id") Long id) 
	{
		Invoice invoice = this.invoiceService.getById(id);
		model.addAttribute("invoice", invoice);
		model.addAttribute("order", invoice.getOrder());
		model.addAttribute("logo", logo);
		model.addAttribute("name", name);
		List<Product> products = this.productService.allProducts();
		model.addAttribute("productList", products);
		
		return "invoices/invoiceedit";
	}
	
	
	@GetMapping(value = "/saveInvoice")
	/* this id is for existing invoice*/
	public String saveInvoice(Model model, @ModelAttribute InputOrderInvoiceDTO inputInvoice) 
	{
		
		
		Invoice invoice = this.invoiceService.add(inputInvoice);
		model.addAttribute("invoice", invoice);
		model.addAttribute("order", invoice.getOrder());
		model.addAttribute("logo", logo);
		model.addAttribute("name", name);
		
		return "invoices/invoiceview.html";
	}
	
	
	
	@GetMapping(value = "modifyInvoice")
	/* this id is for existing invoice*/
	public String modifyInvoice(Model model, @ModelAttribute InputOrderInvoiceDTO inputInvoice) 
	{
		
		
		Invoice invoice = this.invoiceService.add(inputInvoice);
		model.addAttribute("invoice", invoice);
		model.addAttribute("order", invoice.getOrder());
		model.addAttribute("logo", logo);
		model.addAttribute("name", name);
		
		return "invoices/invoiceview.html";
	}
	
	
	
	@RequestMapping(value = "/invoice", method = RequestMethod.GET)
	/* this id is for invoice*/
	public String formInvoice(Model model, @RequestParam(name = "id") Long id) 
	{
		Invoice invoice = this.invoiceService.getById(id);
		model.addAttribute("invoice", invoice);
		model.addAttribute("order", invoice.getOrder());
		model.addAttribute("logo", logo);
		model.addAttribute("name", name);
		
		return "invoices/invoiceview.html";
	}
	
	
	
	
	
	
	@RequestMapping(value = "/createInvoice", method = RequestMethod.GET)
	/* this id is for order so we want to invoice*/
	public String createInvoice(Model model, @RequestParam(name = "id") Long id) 
	{
		Invoice invoice = new Invoice();
		Long index = 1L;
		List<Invoice> invoices = this.invoiceService.allInvoices();
		if(invoices.size()>0) {index = invoices.get(invoices.size()-1).getId()+1;}
		
		invoice.setCreatedAt(new Date());
		Order order = this.orderService.getById(id);
		model.addAttribute("invoice", invoice);
		model.addAttribute("index", index);
		model.addAttribute("order",order);
		
		model.addAttribute("logo", logo);
		model.addAttribute("name", name);
		
		List<Product> products = this.productService.allProducts();
		model.addAttribute("productList", products);
		
		return "invoices/invoiceedit.html";
	}
	
	
	
	
}
