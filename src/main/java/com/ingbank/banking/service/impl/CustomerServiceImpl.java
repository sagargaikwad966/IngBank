package com.ingbank.banking.service.impl;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ingbank.banking.entity.Customer;
import com.ingbank.banking.exception.ApplicationException;
import com.ingbank.banking.model.CustomerRequestModel;
import com.ingbank.banking.repository.CustomerRepository;
import com.ingbank.banking.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService
{

	@Autowired
	CustomerRepository customerRepository;
	
	
	@Override
	public Customer addCustomer(CustomerRequestModel customerRequestModel) throws ApplicationException {
		
		
		Customer customerEntityInput=new Customer();
		
		
		Optional<Customer>customeroptional=customerRepository.findByEmail(customerRequestModel.getEmail());
		
		
		Boolean ispresent=customeroptional.isPresent();
		
		
		if(ispresent)
		{
			Customer customerwithdbemail=customeroptional.get();
			
			
			if(customerRequestModel.getEmail().equals(customerwithdbemail.getEmail()))
			{
				
				throw new ApplicationException("Customer with is this email id already present");
			}
			
		}
		
		
		BeanUtils.copyProperties(customerRequestModel, customerEntityInput);
		
		Customer customerEntityoutput=customerRepository.save(customerEntityInput);
		
		
		return  customerEntityoutput;
	}
	

	@Override
	public Customer getCustomer(Long customerId) throws ApplicationException {
		
		Optional<Customer> findByOptional = customerRepository.findById(customerId);
		Customer customer = new Customer();
		boolean isOptionalPresent = findByOptional.isPresent();
		if(isOptionalPresent)
		{
			customer = findByOptional.get();
		}
		else
		{
			throw new ApplicationException("The Customer Not found with Id : "+customerId);
		}
		return customer;

	}
	
}
