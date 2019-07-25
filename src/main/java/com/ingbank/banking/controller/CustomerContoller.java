package com.ingbank.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ingbank.banking.entity.Customer;
import com.ingbank.banking.exception.ApplicationException;
import com.ingbank.banking.model.CustomerRequestModel;
import com.ingbank.banking.model.ResponseData;
import com.ingbank.banking.service.CustomerService;
import com.ingbank.banking.validation.ApplicationValidation;

@RestController
@RequestMapping("/registerCustomer")
@CrossOrigin
public class CustomerContoller {
	@Autowired
	CustomerService customerService;

	@Autowired
	ApplicationValidation validate;

	@PostMapping("/")
	public ResponseEntity<ResponseData> createCustomer(@RequestBody CustomerRequestModel customerRequestModel)
			throws ApplicationException {

		validate.validateRegisterCustomer(customerRequestModel);

		Customer customer = customerService.addCustomer(customerRequestModel);

		ResponseData response = new ResponseData("HI!!!, " + customer.getFirstName() + " Welcome to Bank "
				+ " Your Customer Id Is:, " + customer.getUserId(), HttpStatus.OK, customer.getUserId());
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
