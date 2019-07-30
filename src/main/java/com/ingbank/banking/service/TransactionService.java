package com.ingbank.banking.service;

import java.net.URISyntaxException;
import java.sql.SQLDataException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ingbank.banking.entity.Transaction;
import com.ingbank.banking.exception.ApplicationException;
import com.ingbank.banking.model.StatementModel;
import com.ingbank.banking.model.TransactionRequestModel;

@Service
public interface TransactionService {

	public Map<String, StatementModel> getYearlyStatement(Long customerId, String year) throws ApplicationException;

	public Transaction getTransactionById(Long id);

	public Transaction doTransaction(TransactionRequestModel transactionRequest)
			throws ApplicationException, SQLDataException, URISyntaxException;

	public String validateOTP(String transactionId, Integer otp) throws URISyntaxException;

	public Transaction confirmTransaction(Long transactionId) throws ApplicationException;

	public List<Transaction> getAllTransactionByUser(Long customerId);

}
