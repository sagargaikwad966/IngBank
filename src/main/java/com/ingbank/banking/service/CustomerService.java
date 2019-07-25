package com.ingbank.banking.service;

import org.springframework.stereotype.Service;

import com.ingbank.banking.entity.Customer;
import com.ingbank.banking.exception.ApplicationException;
import com.ingbank.banking.model.CustomerRequestModel;

@Service
public interface CustomerService {

	public Customer getCustomer(Long customerId) throws ApplicationException;

	public Customer addCustomer(CustomerRequestModel customerRequestModel) throws ApplicationException;

}