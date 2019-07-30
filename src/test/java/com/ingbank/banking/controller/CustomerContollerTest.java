package com.ingbank.banking.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.ingbank.banking.entity.Customer;
import com.ingbank.banking.exception.ApplicationException;
import com.ingbank.banking.model.CustomerRequestModel;
import com.ingbank.banking.model.ResponseData;
import com.ingbank.banking.service.CustomerService;
import com.ingbank.banking.validation.ApplicationValidation;

@RunWith(MockitoJUnitRunner.class)
public class CustomerContollerTest {
	@InjectMocks
	CustomerContoller customerController;

	@Mock
	CustomerService customerService;
	
	@Mock
	 ApplicationValidation validateMock;

	CustomerRequestModel customerrequestmodel = new CustomerRequestModel();
	Customer customer1 = new Customer();
	@Before
	public void setup()
	{
		customerrequestmodel.setDateOfBirth(LocalDate.now());
		customerrequestmodel.setEmail("abc.com");
		customerrequestmodel.setFirstName("ram");
		customerrequestmodel.setGender("male");
		customerrequestmodel.setLastName("mane");
		customerrequestmodel.setPanNumber("COOPP1212");
		customerrequestmodel.setPhoneNumber("9552524243");
		
		
		customer1.setUserId(1L);

	}


	@Test
	public void createCustomerWithSucess() throws ApplicationException {
		Mockito.doNothing().when(validateMock).validateRegisterCustomer(Mockito.any(CustomerRequestModel.class));
		Mockito.when(customerService.addCustomer(Mockito.any(CustomerRequestModel.class))).thenReturn(customer1);
		ResponseEntity<ResponseData> responsedata = customerController.createCustomer(customerrequestmodel);
		assertNotNull(responsedata);
		assertEquals(200, responsedata.getBody().getStatus().value());
		assertEquals(1L, responsedata.getBody().getData());

	}

}