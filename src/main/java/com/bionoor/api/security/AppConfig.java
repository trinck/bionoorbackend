package com.bionoor.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@EnableConfigurationProperties({MailParameters.class, AppParam.class, EmailInfoSender.class})
public class AppConfig {

	
	@Autowired
	private MailParameters mailparam;
	@Autowired
	private AppParam appParam;
	@Autowired
	private EmailInfoSender emailInfoSender;
}
