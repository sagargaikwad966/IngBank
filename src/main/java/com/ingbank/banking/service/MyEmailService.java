package com.ingbank.banking.service;

import org.springframework.stereotype.Service;

@Service
public interface MyEmailService {

	public void sendOtpMessage(String to, String subject, String message);
}
