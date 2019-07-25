package com.ingbank.banking.service;

import org.springframework.stereotype.Service;

import com.ingbank.banking.exception.ApplicationException;

@Service
public interface OtpService {

	public int generateOTP(String key);

	public int getOtp(String key);

	public void clearOTP(String key);

	public int processOtp(Long transactionId) throws ApplicationException;

	public String processValidOtp(int otpnum, String customerId);

}
