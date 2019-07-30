package com.ingbank.banking.service.impl;

import static org.junit.Assert.assertNotNull;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.ingbank.banking.entity.Customer;
import com.ingbank.banking.entity.Transaction;
import com.ingbank.banking.exception.ApplicationException;
import com.ingbank.banking.model.TransactionRequestModel;
import com.ingbank.banking.repository.TransactionRepository;
import com.ingbank.banking.service.CustomerService;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest 
{
	@InjectMocks
	TransactionServiceImpl transactionServiceImpl;
	
	@Mock
	CustomerService customerServiceMock;
	
	@Mock
	TransactionRepository transactionRepository;
	
	TransactionRequestModel transactionRequestModel = new TransactionRequestModel();
	Transaction currentTransaction = new Transaction();
	Transaction lastTransaction = new Transaction();
	Customer customer = new Customer();
	List<Transaction> transactionList = new ArrayList<>();
	List<Transaction> emptyList = new ArrayList<>();
	@Before
	public void setUp()
	{
		transactionRequestModel.setCustomerId(10L);
		transactionRequestModel.setTransactionAmount(500.00);
		transactionRequestModel.setTransactionDescription("EMI");
		transactionRequestModel.setTransactionType("RECEIVE PAYMENT");
		
		
		currentTransaction.setTransactionId(10L);
		lastTransaction.setTransactionId(9L);
		transactionList.add(lastTransaction);
		
		customer.setUserId(1L);
		customer.setTransactionList(emptyList);
		
	}
	
	@Test
	public void testDoTransaction() throws ApplicationException, SQLDataException
	{
		Mockito.when(customerServiceMock.getCustomer(Mockito.anyLong())).thenReturn(customer);
		Mockito.when(transactionRepository.save(currentTransaction));
		Transaction doTransaction = transactionServiceImpl.doTransaction(transactionRequestModel);
		assertNotNull(doTransaction);
		
		
	}

}
