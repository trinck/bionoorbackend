package com.bionoor.api.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "app.param")
public class AppParam {

	
	private String logo;
	private String stockAverage;
	private String name;
}
