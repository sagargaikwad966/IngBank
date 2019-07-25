package com.ingbank.banking.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.ingbank.banking.exception.ApplicationException;
import com.ingbank.banking.service.MyEmailService;
import com.ingbank.banking.service.OtpService;

@RunWith(MockitoJUnitRunner.class)
public class OtpControllerTest {
	
	@InjectMocks
	OtpController otpController;
	
	@Mock
	MyEmailService myEmailService;
	
	
	@Mock
	OtpService otpService;
	
	int otp=121212;
	
	@Test
	public void testGenerateOtpWithSucess() throws ApplicationException
	{
		
		Mockito.when(otpService.processOtp(Mockito.anyLong())).thenReturn(new Integer(121212));
		ResponseEntity<Integer> otpresponse=otpController.generateOtp(1l);
		assertNotNull(otpresponse);
		assertEquals(200, otpresponse.getStatusCode().value());
	}
	
	
	
	@Test
	public void testvalidateOtpWithSucess() throws ApplicationException
	{
		
		Mockito.when(otpService.processValidOtp(Mockito.anyInt(), Mockito.anyString())).thenReturn("");
		ResponseEntity<String> validateresponse=otpController.validateOtp(otp, "1");
		assertNotNull(validateresponse);
		assertEquals(200, validateresponse.getStatusCode().value());
	}
	
	

}
