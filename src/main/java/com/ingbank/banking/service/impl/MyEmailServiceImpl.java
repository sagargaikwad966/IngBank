package com.ingbank.banking.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ingbank.banking.service.MyEmailService;

@Service
public class MyEmailServiceImpl implements MyEmailService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//@Autowired
	//private JavaMailSender javaMailSender;
	
	
	/*
	 * public void sendOtpMessage(String to, String subject, String message) {
	 * 
	 * SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
	 * 
	 * //simpleMailMessage.setFrom("sagargaikwad966@gmail.com");
	 * simpleMailMessage.setTo(to); simpleMailMessage.setSubject(subject);
	 * simpleMailMessage.setText(message); logger.info(subject); logger.info(to);
	 * logger.info(message);
	 * 
	 * //Uncomment to send mail javaMailSender.send(simpleMailMessage); }
	 */
	
		private JavaMailSender mailSender;

		@Autowired
		public MyEmailServiceImpl(JavaMailSender mailSender) {
			this.mailSender = mailSender;
		}
		
		public void sendOtpMessage(String to, String subject, String message) // MailException
		{	
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setFrom("sagargaikwad966@gmail.com");
			msg.setSubject(subject);
			msg.setTo("sagar.gaikwad@hcl.com");
			msg.setText(message);
	        	
	        mailSender.send(msg);
	        
	        logger.info("mail send");
	  
		}
		
		


}
