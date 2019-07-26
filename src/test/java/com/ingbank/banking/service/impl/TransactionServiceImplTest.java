package com.ingbank.banking.service.impl;

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
import com.ingbank.banking.service.CustomerService;
import com.ingbank.banking.service.TransactionService;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest 
{
	@InjectMocks
	TransactionService transactionService;
	
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

}
