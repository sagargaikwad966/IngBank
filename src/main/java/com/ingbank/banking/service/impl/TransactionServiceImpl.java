package com.ingbank.banking.service.impl;

import java.sql.SQLDataException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ingbank.banking.entity.Customer;
import com.ingbank.banking.entity.Transaction;
import com.ingbank.banking.exception.ApplicationException;
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
			throws ApplicationException, SQLDataException 
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
		
		if(fetchLastTransaction.getBalance() == null)
			newTransaction.setBalance(transactionRequest.getTransactionAmount());
		else
			newTransaction.setBalance(transactionRequest.getTransactionAmount() + fetchLastTransaction.getBalance());

		return transactionRepository.save(newTransaction);

	}
		



}