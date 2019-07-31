package com.ingbank.banking.service.impl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ingbank.banking.entity.User;
import com.ingbank.banking.repository.UserRepository;

@Service 
public class UserServiceImpl 
{
	@Autowired
	UserRepository userRepository;

	public User createUser(String name) throws NoSuchAlgorithmException 
	{
		User user = new User();
		user.setUserId(customizeId());
		user.setName(name);
		return userRepository.save(user);
	}

	private String customizeId() throws NoSuchAlgorithmException {
		Random random = SecureRandom.getInstanceStrong();
		Integer randomNumber = (random.nextInt(655) - 327);

		   if (randomNumber < 0) 
		   {
		    randomNumber = randomNumber * (-1);
		   }
		   return "USER" + randomNumber.toString();

	}

	public List<User> getUserList() {
		return userRepository.findAll();
	}

	public User getUser(String userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		return optionalUser.get();
	}

}
