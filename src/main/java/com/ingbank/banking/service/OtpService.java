package com.ingbank.banking.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

import com.ingbank.banking.exception.ApplicationException;

@Service
public interface OtpService {

	public int generateOTP(String key) throws NoSuchAlgorithmException;

	public int getOtp(String key);

	public void clearOTP(String key);

	public int processOtp(String referenceId) throws ApplicationException, NoSuchAlgorithmException;

	public boolean processValidOtp(int otpnum, String referenceId);

}
