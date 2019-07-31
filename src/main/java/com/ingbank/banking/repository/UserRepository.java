package com.ingbank.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ingbank.banking.entity.User;

public interface UserRepository extends JpaRepository<User, String>{

}
