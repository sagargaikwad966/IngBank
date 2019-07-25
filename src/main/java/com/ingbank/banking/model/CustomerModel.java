package com.ingbank.banking.model;

import java.time.LocalDate;
import java.util.List;

import com.ingbank.banking.entity.Transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerModel

{

	private Long userId;

	private String email;

	private String lastName;

	private String firstName;

	private LocalDate dateOfBirth;

	private String phoneNumber;

	private String panNumber;

	private String gender;

	List<Transaction> transactionlist;

}
