package com.ingbank.banking.service.impl;

import static org.junit.Assert.assertNotNull;

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
import com.ingbank.banking.service.CustomerService;
import com.ingbank.banking.service.TransactionService;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest 
{
	@InjectMocks
	TransactionServiceImpl transactionServiceImpl;

	@Mock
	CustomerService customerServiceMock;

	Transaction transaction = new Transaction();
	Customer customer = new Customer();

	@Before
	public void setUp()
	{

		transaction.setTransactionId(1L);
		customer.setUserId(1L);

	}

	@Test
	public void testDoTransaction() throws ApplicationException
	{
		Mockito.when(customerServiceMock.getCustomer(Mockito.anyLong())).thenReturn(customer);

	}

	@Test
	public void testgetYearlyStatement() throws ApplicationException {
		Long customerId = 1L;
		Customer customer = new Customer();
		customer.setUserId(1L);
		
		Transaction transaction = new Transaction();

		transaction.setTransactionType("RECEIVE PAYMENT");
		transaction.setTransactionAmount(2000.00);
		transaction.setTransactionDateTime(LocalDateTime.now());
		
		List transactionList = new ArrayList();
		transactionList.add(transaction);
		customer.setTransactionList(transactionList);
		
    Mockito.when(customerServiceMock.getCustomer(customerId)).thenReturn(customer);
    
    Map<String, StatementModel> transactionsMap = transactionServiceImpl.getYearlyStatement(customerId, "2019");
    
    assertNotNull(transactionsMap);
    assetEquals(transactionsMap.get(key), "JULY");
    
	}
}
