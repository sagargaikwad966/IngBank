package com.ingbank.banking.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URISyntaxException;
import java.sql.SQLDataException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.ingbank.banking.model.StatementModel;
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
	
	Transaction transaction = new Transaction();
	
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
	public void testDoTransaction() throws ApplicationException, SQLDataException, URISyntaxException
	{
		Mockito.when(customerServiceMock.getCustomer(Mockito.anyLong())).thenReturn(customer);
		Mockito.when(transactionRepository.save(currentTransaction));
		Transaction doTransaction = transactionServiceImpl.doTransaction(transactionRequestModel);
		assertNotNull(doTransaction);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testgetYearlyStatement() throws ApplicationException {
		
		transaction.setTransactionId(1L);
		customer.setUserId(1L);
		Long customerId = 1L;
		Customer customer = new Customer();
		customer.setUserId(1L);
		
		transaction.setTransactionType("RECEIVE PAYMENT");
		transaction.setTransactionAmount(2000.00);
		transaction.setTransactionDateTime(LocalDateTime.now());
		
		transactionList.add(transaction);
		customer.setTransactionList(transactionList);
		
    Mockito.when(customerServiceMock.getCustomer(customerId)).thenReturn(customer);
    
    Map<String, StatementModel> transactionsMap = transactionServiceImpl.getYearlyStatement(customerId, "2019");
    
    assertNotNull(transactionsMap);
    assertEquals(2000, transactionsMap.get(0).getTotalIncoming().doubleValue());
    
	}
}
