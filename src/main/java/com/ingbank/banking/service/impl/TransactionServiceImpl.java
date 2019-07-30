package com.ingbank.banking.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLDataException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ingbank.banking.entity.Customer;
import com.ingbank.banking.entity.Transaction;
import com.ingbank.banking.exception.ApplicationException;
import com.ingbank.banking.model.EmailModel;
import com.ingbank.banking.model.ResponseData;
import com.ingbank.banking.model.StatementModel;
import com.ingbank.banking.model.TransactionRequestModel;
import com.ingbank.banking.repository.CustomerRepository;
import com.ingbank.banking.repository.TransactionRepository;
import com.ingbank.banking.service.CustomerService;
import com.ingbank.banking.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	CustomerService customerService;

	@Autowired
	TransactionService TransactionService;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
	RestTemplate restTemplate;


	public Map<String, StatementModel> getYearlyStatement(Long customerId, String year) throws ApplicationException{ 

		Map<String, StatementModel> transactionsMap = new HashMap<String, StatementModel>();
		Customer customer = customerService.getCustomer(customerId);
		List<Transaction> transactionList = customer.getTransactionList();

		for (Transaction transaction : transactionList) {
			StatementModel statementModel = new StatementModel();
			statementModel.setTotalIncoming(0.00);
			statementModel.setTotalOutgoing(0.00);
			statementModel.setClosingBalance(0.00);
			statementModel.setCustomerId(customerId);

			String month = transaction.getTransactionDateTime().getMonth().name();
			
			if (transaction.getTransactionType().equalsIgnoreCase("RECEIVE PAYMENT")) {
				if (transactionsMap.containsKey(month)) {
					statementModel.setTotalIncoming(
							transaction.getTransactionAmount() + transactionsMap.get(month).getTotalIncoming());
					statementModel.setTotalOutgoing(transactionsMap.get(month).getTotalOutgoing());
				} else {
					statementModel.setTotalIncoming(transaction.getTransactionAmount());
				}
				transactionsMap.put(month, statementModel);
			} else {
				if (transactionsMap.containsKey(month)) {
					statementModel.setTotalIncoming(transactionsMap.get(month).getTotalIncoming());
					statementModel.setTotalOutgoing(
							transaction.getTransactionAmount() + transactionsMap.get(month).getTotalOutgoing());
				} else {
					statementModel.setTotalOutgoing(transaction.getTransactionAmount());
				}

				transactionsMap.put(month, statementModel);
			}
		}
		for (String month : transactionsMap.keySet()) {
			StatementModel model = transactionsMap.get(month);
			Double closingBalance = model.getTotalIncoming() - model.getTotalOutgoing();
			model.setClosingBalance(closingBalance);
		}

		return transactionsMap;
	}
	

	public Transaction getTransactionById(Long id) 
	{
		Optional<Transaction> transactionOptional = transactionRepository.findById(id);
		Transaction transaction = new Transaction();
		boolean isOptionalPresent= transactionOptional.isPresent();
		if(isOptionalPresent) {
			transaction = transactionOptional.get();
		}
		return transaction;
	}
	
	@Override
	public Transaction doTransaction(TransactionRequestModel transactionRequest)
			throws ApplicationException, SQLDataException, URISyntaxException 
	{
		Customer customer = customerService.getCustomer(transactionRequest.getCustomerId());	
		
		Transaction currentTransaction = new Transaction();
		Transaction fetchLastTransaction = new Transaction();
		
		if(customer.getTransactionList().isEmpty())
		{
			if (transactionRequest.getTransactionType().equalsIgnoreCase("RECEIVE PAYMENT")) 
			{
				currentTransaction = receivePayment(transactionRequest, fetchLastTransaction, customer);
			}
			else if (transactionRequest.getTransactionType().equalsIgnoreCase("MAKE PAYMENT"))
			{
				throw new ApplicationException(
						"Hi," + customer.getFirstName() + " you dont have sufficient balance");
			}
		}
		else
		{
			fetchLastTransaction = customer.getTransactionList().get(customer.getTransactionList().size()-1);
			if (transactionRequest.getTransactionType().equalsIgnoreCase("RECEIVE PAYMENT")) 
			{
				currentTransaction = receivePayment(transactionRequest, fetchLastTransaction, customer);
			} 
			else if (transactionRequest.getTransactionType().equalsIgnoreCase("MAKE PAYMENT"))
			{
				currentTransaction = makePayment(transactionRequest, fetchLastTransaction, customer);
			}
		}
		
		Integer otp = generateOTP(currentTransaction.getTransactionId().toString());
		
		sendOTPMail(customer.getEmail(),"OTP FOR "+currentTransaction.getTransactionId().toString(), otp);
		
		return currentTransaction;
	}



	private Transaction makePayment(TransactionRequestModel transactionRequest, Transaction fetchLastTransaction, Customer customer)
			throws ApplicationException 
	{
		if (fetchLastTransaction.getBalance() < transactionRequest.getTransactionAmount())
			throw new ApplicationException(
					"Hi," + customer.getFirstName() + " you dont have sufficient balance");
		else {

			Transaction newTransaction = new Transaction();
			newTransaction.setTransactionType(transactionRequest.getTransactionType());
			newTransaction.setTransactionDescription(transactionRequest.getTransactionDescription());
			newTransaction.setStatus("PENDING");
			newTransaction.setTransactionDateTime(LocalDateTime.now());
			newTransaction.setTransactionAmount(transactionRequest.getTransactionAmount());
			newTransaction.setBalance(fetchLastTransaction.getBalance() - transactionRequest.getTransactionAmount());
			newTransaction.setCustomer(customer);
				return transactionRepository.save(newTransaction);
		}
	}
	private Transaction receivePayment(TransactionRequestModel transactionRequest, Transaction fetchLastTransaction, Customer customer) {
		
		Transaction newTransaction = new Transaction();
		newTransaction.setTransactionType(transactionRequest.getTransactionType());
		newTransaction.setTransactionDescription(transactionRequest.getTransactionDescription());
		newTransaction.setStatus("PENDING");
		newTransaction.setTransactionDateTime(LocalDateTime.now());
		newTransaction.setTransactionAmount(transactionRequest.getTransactionAmount());
		newTransaction.setCustomer(customer);
		
		if(null == fetchLastTransaction.getCustomer())
			newTransaction.setBalance(transactionRequest.getTransactionAmount()); 
		else
			newTransaction.setBalance(transactionRequest.getTransactionAmount() +fetchLastTransaction.getBalance());
		 

		return transactionRepository.save(newTransaction);

	}
		
	private Integer generateOTP(String referenceId) throws URISyntaxException 
	{
		final String baseUrl = "http://localhost:9090/ingbank/otp/generateOtp/";
		URI uri = new URI(baseUrl);

		return restTemplate.getForObject(uri + referenceId, Integer.class);
		
	}
	

	private void sendOTPMail(String email, String subject, Integer otp) throws URISyntaxException 
	{
		final String baseUrl = "http://localhost:9090/ingbank/notifications/email";
		URI uri = new URI(baseUrl);

		EmailModel emailModel = new EmailModel(email, subject, String.valueOf(otp));
		
		restTemplate.postForEntity(uri, emailModel, ResponseData.class);
	}
	
	@Override
	public String validateOTP(String transactionId, Integer otp) throws URISyntaxException 
	{
		final String baseUrl = "http://localhost:9090/ingbank/otp/validateOtp/";
		URI uri = new URI(baseUrl);
		
		return restTemplate.getForObject(uri + transactionId+"/"+otp, String.class);
	}


	@Override
	public Transaction confirmTransaction(Long transactionId) throws ApplicationException 
	{
		Transaction transaction = getTransactionById(transactionId);
		Transaction fetchLastTransaction = new Transaction();
		
		Customer customer = customerService.getCustomer(transaction.getCustomer().getUserId());
		
		if(customer.getTransactionList().isEmpty())
		{
			transaction.setBalance(transaction.getTransactionAmount());
			transaction.setStatus("CONFIRM");
			transaction = transactionRepository.save(transaction);

		}
		else
		{
			fetchLastTransaction = customer.getTransactionList().get(customer.getTransactionList().size()-1);
			
			if (transaction.getTransactionType().equalsIgnoreCase("RECEIVE PAYMENT")) 
			{
				transaction.setBalance(transaction.getTransactionAmount() + fetchLastTransaction.getBalance());
				transaction.setStatus("CONFIRM");
				transaction = transactionRepository.save(transaction);
			} 
			else if (transaction.getTransactionType().equalsIgnoreCase("MAKE PAYMENT"))
			{
				transaction.setBalance(fetchLastTransaction.getBalance() - transaction.getTransactionAmount());
				transaction.setStatus("CONFIRM");
				transaction = transactionRepository.save(transaction);
			}
		}
		return transaction;
		
	}


	@Override
	public List<Transaction> getAllTransactionByUser(Long customerId) {
		
		Optional<List<Transaction>> transactionOptional = transactionRepository.findByCustomerUserIdAndStatus(customerId, "CONFIRM");
		List<Transaction> transactionList = new ArrayList<>();
		
		boolean isOptionalPresent= transactionOptional.isPresent();
		if(isOptionalPresent) 
		{
			transactionList = transactionOptional.get();
		}
		return transactionList;
	}


}