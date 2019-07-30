package com.ingbank.banking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ingbank.banking.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>
{
	
	public Optional<List<Transaction>> findByCustomerUserIdAndStatus(Long userId, String status);
	


}
