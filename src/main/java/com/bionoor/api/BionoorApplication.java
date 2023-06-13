package com.bionoor.api;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.bionoor.api.repositories.CustomerRepository;
import com.bionoor.api.repositories.PaymentMethodRepository;
import com.bionoor.api.services.OrderItemService;
import com.bionoor.api.services.OrderService;
import com.bionoor.api.services.ProductService;
import com.bionoor.api.utils.ServiceStorageIn;
@PropertySource("classpath:app.properties")
@SpringBootApplication
@EnableJpaAuditing
public class BionoorApplication  implements CommandLineRunner{

	@Autowired
	ServiceStorageIn serviceStorageIn;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	OrderItemService itemService;
	
	@Autowired
	PaymentMethodRepository paymentMethodRepository ;
	
	@Autowired
	ProductService productService;
	public static void main(String[] args) {
		SpringApplication.run(BionoorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		this.serviceStorageIn.init();
		
		
		
	}

}
