package com.ingbank.banking.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@GetMapping("/generateOtp")
	public ResponseEntity<Integer> generateOtp(@RequestParam("transactionId") Long transactionId)
			throws ApplicationException {

		int otp = otpService.processOtp(transactionId);

		myEmailService.sendOtpMessage("sssaurabhsuman@gmail.com", "OTP", String.valueOf(otp));

		logger.info("OTP : " + otp);
		return new ResponseEntity<Integer>(otp, HttpStatus.OK);

	}

	@RequestMapping(value = "/validateOtp", method = RequestMethod.GET)
	public ResponseEntity<String> validateOtp(@RequestParam("otpnum") int otpnum,
			@RequestParam("customerId") String customerId) {
		String response = otpService.processValidOtp(otpnum, customerId);
		// ResponseData response = new ResponseData();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
