package com.ingbank.banking.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ingbank.banking.exception.ApplicationException;
import com.ingbank.banking.model.CustomerRequestModel;
import com.ingbank.banking.model.TransactionRequestModel;


@Component
public class ApplicationValidation {



	public void validateRegisterCustomer(CustomerRequestModel customerRequestModel) throws ApplicationException {
		
		
		
		if (!StringUtils.hasText(customerRequestModel.getPanNumber())||!StringUtils.hasText(customerRequestModel.getFirstName())||!StringUtils.hasText(customerRequestModel.getEmail()))
				
			throw new ApplicationException("Please fill Mandatory Fields");

		
			
		}

	public void validateTransactionRequest(TransactionRequestModel transactionRequest) throws ApplicationException 
	{
		if(!StringUtils.hasText(transactionRequest.getTransactionType()))
			throw new ApplicationException("Please enter TransactionType");
		
		if(!StringUtils.hasText(transactionRequest.getTransactionDescription()))
			throw new ApplicationException("Please enter TransactionDescription");
		
		if(ObjectUtils.isEmpty(transactionRequest.getCustomerId()))
			throw new ApplicationException("Please enter Customer Id");
		
		if(ObjectUtils.isEmpty(transactionRequest.getTransactionAmount()))
			throw new ApplicationException("Please enter TransactionAmount");
		
	}
	}
	
	


