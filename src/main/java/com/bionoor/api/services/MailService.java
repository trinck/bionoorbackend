package com.bionoor.api.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.bionoor.api.models.Customer;
import com.bionoor.api.models.Order;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService implements MailServiceIn{

	
	@Autowired
	 private  JavaMailSender mailSender;
	@Autowired()
	private SpringTemplateEngine thymeleafTemplateEngine;
	
	@Value("classpath:/static/images/logo2.png")
	Resource resourceFile;
	
	@Override
	public void sendConfirmationOrder(Order order) {
		// TODO Auto-generated method stub
		
	Map<String, Object> templateModel = Map.of("order", order);
	
	Context thymeleafContext = new Context();
	thymeleafContext.setVariables(templateModel);
		 String htmlBody = thymeleafTemplateEngine.process("template-thymeleaf.html", thymeleafContext);
		 
	 MimeMessage message = mailSender.createMimeMessage();
		    try {
				MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
				
				helper.setFrom("trinckmouloungui@gmail.com");
				helper.setTo("g.mouloungui_ibiatsi@mundiapolis.ma");
				helper.setSubject("Confirmation de commande");
			helper.setText(htmlBody, true);
				helper.addInline("attachment.png", resourceFile);
			} catch (MessagingException e) {
			// TODO Auto-generated catch block
								e.printStackTrace();
				throw new MailSendException(e.getMessage());
			}
		
		 
        mailSender.send(message);
    }
		
	

	@Override
	public void sendVerificationEmail(Customer customer) {
		// TODO Auto-generated method stub
		Map<String, Object> templateModel = Map.of("customer", customer);
		
		Context thymeleafContext = new Context();
		thymeleafContext.setVariables(templateModel);
			 String htmlBody = thymeleafTemplateEngine.process("template-emailverification.html", thymeleafContext);
			 
		 MimeMessage message = mailSender.createMimeMessage();
			    try {
					MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
					
					helper.setFrom("trinckmouloungui@gmail.com");
					helper.setTo("g.mouloungui_ibiatsi@mundiapolis.ma");
					helper.setSubject("Email verification");
				helper.setText(htmlBody, true);
					helper.addInline("attachment.png", resourceFile);
				} catch (MessagingException e) {
				// TODO Auto-generated catch block
									e.printStackTrace();
					throw new MailSendException(e.getMessage());
				}
			
        mailSender.send(message);
	}

}
