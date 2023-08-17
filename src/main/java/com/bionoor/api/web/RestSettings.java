package com.bionoor.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bionoor.api.security.AppConfig;

@RestController
@RequestMapping("api/settings")
public class RestSettings {

	
	@Autowired
	private AppConfig appConfig;
	
	@GetMapping("/")
	public AppConfig getSettings() {
		
		return appConfig;
	}
	
	
	
	@GetMapping("/app-param/setName")
	public AppConfig setAppName(@RequestParam String name) {
		
		
		appConfig.getAppParam().setName(name);
		return appConfig;
	}
}
