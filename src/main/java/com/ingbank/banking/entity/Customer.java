package com.ingbank.banking.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
public class Customer implements Serializable
{

	private static final long serialVersionUID = -6490058054767591976L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "customer_id", nullable=false)
		private Long userId;
		
		@Email
		@Column(name = "email", nullable=false)
		private String email;
		
		@Column(name = "last_name", nullable=false)
		private String lastName;
		
		@Column(name = "first_name", nullable=false)
		private String firstName;
		
		@Column(name = "date_of_birth", nullable=false)
		private LocalDate dateOfBirth;
		
		@Column(name = "phone_number", nullable=false)
		private String phoneNumber;
		
		@Column(name = "pan_number", nullable=false)
		private String panNumber;
		
		@Column(name = "gender", nullable=false)
		private String gender;
		
		@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "customer")
		private List<Transaction> transactionList;
		
	}

	
	
	