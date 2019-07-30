package com.ingbank.banking.service;

import org.springframework.stereotype.Service;

import com.ingbank.banking.model.EmailModel;

@Service
public interface MyEmailService {

	public void sendMail(EmailModel emailModel);
}
