package com.ingbank.banking.service.impl;

import static org.junit.Assert.assertNotNull;

<<<<<<< HEAD
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;
=======
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
>>>>>>> 27b557ecd1ac473e0802baf1073b813f8cbf0ca9

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
<<<<<<< HEAD
import com.ingbank.banking.model.TransactionRequestModel;
import com.ingbank.banking.repository.TransactionRepository;
=======
import com.ingbank.banking.model.StatementModel;
>>>>>>> 27b557ecd1ac473e0802baf1073b813f8cbf0ca9
import com.ingbank.banking.service.CustomerService;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest 
{
	@InjectMocks
	TransactionServiceImpl transactionServiceImpl;
<<<<<<< HEAD
	
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
		
=======

	@Mock
	CustomerService customerServiceMock;

	Transaction transaction = new Transaction();
	Customer customer = new Customer();

	@Before
	public void setUp()
	{

		transaction.setTransactionId(1L);
		customer.setUserId(1L);

>>>>>>> 27b557ecd1ac473e0802baf1073b813f8cbf0ca9
	}

	@Test
	public void testDoTransaction() throws ApplicationException, SQLDataException
	{
		Mockito.when(customerServiceMock.getCustomer(Mockito.anyLong())).thenReturn(customer);
<<<<<<< HEAD
		Mockito.when(transactionRepository.save(currentTransaction));
		Transaction doTransaction = transactionServiceImpl.doTransaction(transactionRequestModel);
		assertNotNull(doTransaction);
		
		
=======

>>>>>>> 27b557ecd1ac473e0802baf1073b813f8cbf0ca9
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
