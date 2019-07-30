package com.ingbank.banking.controller;

import java.net.URISyntaxException;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ingbank.banking.entity.Customer;
import com.ingbank.banking.entity.Transaction;
import com.ingbank.banking.exception.ApplicationException;
import com.ingbank.banking.model.ResponseData;
import com.ingbank.banking.model.StatementModel;
import com.ingbank.banking.model.TransactionHistoryModel;
import com.ingbank.banking.model.TransactionRequestModel;
import com.ingbank.banking.service.CustomerService;
import com.ingbank.banking.service.TransactionService;
import com.ingbank.banking.validation.ApplicationValidation;

@RestController
@RequestMapping("/transaction")
@CrossOrigin
public class TransactionController {

	@Autowired
	CustomerService customerService;

	@Autowired
	ApplicationValidation applicationValidation;

	private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	TransactionService transactionService;

	@GetMapping("/viewMonthlyStatement/{customerId}/{year}")
	public ResponseEntity<ResponseData> viewMonthlyStatement(@PathVariable(value = "customerId") Long customerId,
			@PathVariable(value = "year") String year) throws ApplicationException {
		
		Map<String, StatementModel> statementMap = null;

		statementMap = transactionService.getYearlyStatement(customerId, year);
		ResponseData response = new ResponseData("\"Your monthwise account statement for the year of " + year + ": ",
				HttpStatus.OK, statementMap);

		return new ResponseEntity<ResponseData>(response, HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<ResponseData> doTransaction(@RequestBody TransactionRequestModel transactionRequest)
			throws SQLDataException, ApplicationException, URISyntaxException {
		logger.debug("CONTROLLER : TransactionController METHOD : doTransaction() enter");

		applicationValidation.validateTransactionRequest(transactionRequest);

		Transaction doTransaction = transactionService.doTransaction(transactionRequest);
		Customer customer = customerService.getCustomer(transactionRequest.getCustomerId());

		String data = " /Transaction Id : " + doTransaction.getTransactionId() + " /Description : "
				+ doTransaction.getTransactionDescription() + " /Amount : " + doTransaction.getTransactionAmount() + "/";
		ResponseData response = new ResponseData(
				"Hi, " + customer.getFirstName() + " your transaction needs to validate", HttpStatus.OK, data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/validate")
	public ResponseEntity<ResponseData> doConfirmTransaction(@RequestParam("otpnum") int otpnum, @RequestParam("transactionId") Long transactionId)
			throws SQLDataException, ApplicationException, URISyntaxException {

		String validationResult = transactionService.validateOTP(String.valueOf(transactionId), otpnum);
		Transaction confirmTransaction = new Transaction();
		Customer customer = new Customer();
		
		if(validationResult.equalsIgnoreCase("Valid")) 
		{
			confirmTransaction = transactionService.confirmTransaction(transactionId);
			customer = confirmTransaction.getCustomer();
		} else
			throw new ApplicationException("Invalid OTP");
		

		String data = " /Transaction Id : " + confirmTransaction.getTransactionId() + " /Description : "
				+ confirmTransaction.getTransactionDescription() + " /Balance : " + confirmTransaction.getBalance() + "/";
		ResponseData response = new ResponseData(
				"Hi, " + customer.getFirstName() + " your transaction done successfully", HttpStatus.OK, data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/transactionHistory")
	public ResponseEntity<ResponseData> viewTransactionHistory(@RequestParam("customerId") Long userId)
			throws ApplicationException 
	{
		
		Customer customer = customerService.getCustomer(userId);
		
		if(customer.getTransactionList().isEmpty())
			throw new ApplicationException("Hi, "+customer.getFirstName()+" you dont have any transaction history");
		else
		{
			Collections.reverse(customer.getTransactionList());
			
			List<TransactionHistoryModel> transactionModelList = new ArrayList<>();
			transactionModelList = mappingEntityToModel(customer.getTransactionList(),transactionModelList);
			
			ResponseData response = new ResponseData("Hi, "+customer.getFirstName()+" please find below your last 10 transaction history", HttpStatus.OK, transactionModelList.subList(0, 2));
			return new ResponseEntity<ResponseData>(response, HttpStatus.OK);
		}
		
	}

	private List<TransactionHistoryModel> mappingEntityToModel(List<Transaction> transactionList,
			List<TransactionHistoryModel> transactionModelList) 
	{
		for(Transaction transaction : transactionList)
		{
			TransactionHistoryModel transactionModel = new TransactionHistoryModel();
			BeanUtils.copyProperties(transaction, transactionModel);
			transactionModelList.add(transactionModel);
		}
	return 	transactionModelList;
	}
}
