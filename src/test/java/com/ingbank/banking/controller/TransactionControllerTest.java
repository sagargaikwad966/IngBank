package com.ingbank.banking.controller;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.ingbank.banking.service.CustomerService;
import com.ingbank.banking.service.TransactionService;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest 
{
	@InjectMocks
	TransactionController transactionController;
	
	@Mock
	TransactionService transactionServiceMock;
	
	@Mock
	CustomerService customerServiceMock;

}
