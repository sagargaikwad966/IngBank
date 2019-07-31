package com.ingbank.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ingbank.banking.model.EmailModel;
import com.ingbank.banking.model.ResponseData;
import com.ingbank.banking.service.MyEmailService;

@RestController
@RequestMapping("notifications")
public class NotificationController 
{
	@Autowired
	MyEmailService myEmailService;
	
	@PostMapping("/email")
	public ResponseEntity<ResponseData> sendEmail(@RequestBody EmailModel emailModel)
	{
		myEmailService.sendMail(emailModel);
		ResponseData response = new ResponseData("Mail Sent", HttpStatus.OK, emailModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
