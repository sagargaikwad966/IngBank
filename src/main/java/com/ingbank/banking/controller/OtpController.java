package com.ingbank.banking.controller;

import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ingbank.banking.exception.ApplicationException;
import com.ingbank.banking.service.MyEmailService;
import com.ingbank.banking.service.OtpService;

@RestController
@RequestMapping("/otp")
@CrossOrigin
public class OtpController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public OtpService otpService;

	@Autowired
	public MyEmailService myEmailService;
	
	

	@GetMapping("/generateOtp/{referenceId}")
	public ResponseEntity<Integer> generateOtp(@PathVariable("referenceId") String referenceId)
			throws ApplicationException, NoSuchAlgorithmException, URISyntaxException {

		int otp = otpService.processOtp(referenceId);
		
		logger.info("OTP : " + otp);

		return new ResponseEntity<Integer>(otp, HttpStatus.OK);

	}

	

	@GetMapping(value = "/validateOtp/{referenceId}/{otpnum}")
	public ResponseEntity<String> validateOtp(@PathVariable("otpnum") int otpnum, @PathVariable("referenceId") String referenceId) 
	{
		boolean response = otpService.processValidOtp(otpnum, referenceId);
		if(response)
			return new ResponseEntity<>("Valid", HttpStatus.OK);
		else
			return new ResponseEntity<>("Invalid", HttpStatus.OK);
	}

}
