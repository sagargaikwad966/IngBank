package com.ingbank.banking.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "transactionId")
public class Transaction implements Serializable
{
	
	private static final long serialVersionUID = -7394752514121575923L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id")
	private Long transactionId;
	
	@Column(name = "transaction_date", nullable=false)
	private LocalDateTime transactionDateTime;
	
	@Column(name = "transaction_type", nullable=false)
	private String transactionType;
	
	@Column(name = "transaction_description", nullable=false)
	private String transactionDescription;
	
	@Column(name = "transaction_amount")
	private Double transactionAmount;
	
	@Column(name = "status", nullable=false)
	private String status;
	
	@Column(name = "balance")
	private Double balance;
	
	@JoinColumn(name = "customer_id")
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Customer customer;
	

}
