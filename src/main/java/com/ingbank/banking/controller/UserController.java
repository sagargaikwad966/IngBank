package com.ingbank.banking.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ingbank.banking.entity.User;
import com.ingbank.banking.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/user")
public class UserController 
{
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@PostMapping("/create")
	public User createUser(@RequestParam("name") String name) throws NoSuchAlgorithmException
	{
		return userServiceImpl.createUser(name);
	}
	
	@PostMapping("/all")
	public List<User> getAll() throws NoSuchAlgorithmException
	{
		return userServiceImpl.getUserList();
	}
	
	@PostMapping("/{id}")
	public User getUser(@PathVariable("id") String userId) throws NoSuchAlgorithmException
	{
		return userServiceImpl.getUser(userId);
	}

}
