package com.bionoor.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
@PropertySource("classpath:app.properties")
@SpringBootApplication
public class BionoorApplication  implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(BionoorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
