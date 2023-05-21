package com.bionoor.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import com.bionoor.api.utils.ServiceStorageIn;
@PropertySource("classpath:app.properties")
@SpringBootApplication
public class BionoorApplication  implements CommandLineRunner{

	@Autowired
	ServiceStorageIn serviceStorageIn;
	public static void main(String[] args) {
		SpringApplication.run(BionoorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		this.serviceStorageIn.init();
	}

}
