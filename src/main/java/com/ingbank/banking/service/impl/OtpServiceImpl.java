package com.ingbank.banking.service.impl;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.ingbank.banking.entity.Customer;
import com.ingbank.banking.entity.Transaction;
import com.ingbank.banking.exception.ApplicationException;
import com.ingbank.banking.service.CustomerService;
import com.ingbank.banking.service.OtpService;
import com.ingbank.banking.service.TransactionService;

@Service
public class OtpServiceImpl implements OtpService {
	
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	CustomerService customerService;
	
	//cache based on username and OPT MAX 8 
	 private static final Integer EXPIRE_MINS = 5;
	 private LoadingCache<String, Integer> otpCache;
	 
	 public OtpServiceImpl(){
		 super();
		 otpCache = CacheBuilder.newBuilder().
		     expireAfterWrite(
		    		 EXPIRE_MINS, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
		    			 	public Integer load(String key) {
		    			 				return 0;
		    			 	}
		    		 });
		 }
	 
	//This method is used to push the opt number against Key. Rewrite the OTP if it exists
	 //Using user id  as key
	 public int generateOTP(String key){
		 Random random = new Random();
		 int otp = 100000 + random.nextInt(900000);
		 otpCache.put(key, otp);
		 return otp;
		  }
	 
	 //This method is used to return the OPT number against Key->Key values is username
	 public int getOtp(String key){ 
		 try{
			 return otpCache.get(key); 
		 }catch (Exception e){
			 return 0; 
		 }
	 }
	 
	//This method is used to clear the OTP catched already
	public void clearOTP(String key){ 
		otpCache.invalidate(key);
	 }
	
	public int processOtp(Long transactionId) throws ApplicationException {
		
		Customer customer = new Customer();
		Transaction transaction = transactionService.getTransactionById(transactionId);
		if(transaction != null ) {
			customer = transaction.getCustomer();
		}
		return generateOTP(String.valueOf(customer.getUserId()));
	}
	
	
	 public String processValidOtp(int otpnum,String customerId) {
		 final String SUCCESS = "Entered Otp is valid. Transaction Successful";
			final String FAIL = "Entered Otp is NOT valid. Please Retry!";
			//logger.info(" Otp Number : "+otpnum);
		
			//Validate the Otp 
			
			if(otpnum >= 0){
				int serverOtp = getOtp(customerId);
				if(serverOtp > 0){
					if(otpnum == serverOtp){
						clearOTP(customerId);
						return (FAIL);
					}else{
						
						
						return SUCCESS;
					}
				}else {
					return FAIL;
				}
			}else {
			return FAIL;
			}
			
	 }
}
