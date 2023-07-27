package com.bionoor.api;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.bionoor.api.utils.ServiceStorageIn;


@PropertySource("classpath:app.properties")
@SpringBootApplication
@EnableJpaAuditing

public class BionoorApplication  implements CommandLineRunner{

	@Autowired
	private ServiceStorageIn serviceStorageIn;
	

	public static void main(String[] args) {
		SpringApplication.run(BionoorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		this.serviceStorageIn.init();
		
				
	}
	
	
	
}
