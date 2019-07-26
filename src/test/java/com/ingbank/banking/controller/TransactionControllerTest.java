package com.ingbank.banking.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLDataException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.ingbank.banking.entity.Customer;
import com.ingbank.banking.entity.Transaction;
import com.ingbank.banking.exception.ApplicationException;
import com.ingbank.banking.model.ResponseData;
import com.ingbank.banking.model.TransactionRequestModel;
import com.ingbank.banking.service.CustomerService;
import com.ingbank.banking.service.TransactionService;
import com.ingbank.banking.validation.ApplicationValidation;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest 
{
	@InjectMocks
	TransactionController transactionController;
	
	@Mock
	TransactionService transactionServiceMock;
	
	@Mock
	CustomerService customerServiceMock;
	
	@Mock
	ApplicationValidation validationMock;
	
	Transaction transaction = new Transaction();
	Customer customer = new Customer();
	TransactionRequestModel transactionRequestModel = new TransactionRequestModel();
	
	@Before
	public void setUp()
	{
		
		transaction.setTransactionId(1L);
		customer.setUserId(1L);
		transactionRequestModel.setCustomerId(1L);
		
	}
	
	@Test
	public void testDoTransaction() throws ApplicationException, SQLDataException
	{
		Mockito.doNothing().when(validationMock).validateTransactionRequest(Mockito.any(TransactionRequestModel.class));
		Mockito.when(transactionServiceMock.doTransaction(Mockito.any(TransactionRequestModel.class))).thenReturn(transaction);
		Mockito.when(customerServiceMock.getCustomer(Mockito.anyLong())).thenReturn(customer);
		
		ResponseEntity<ResponseData> response = transactionController.doTransaction(transactionRequestModel);
		
		assertNotNull(response);
		assertEquals(200, response.getBody().getResponseStatus().value());
	}

}
