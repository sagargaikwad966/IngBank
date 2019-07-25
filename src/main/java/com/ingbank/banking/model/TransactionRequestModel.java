package com.ingbank.banking.model;

import lombok.Data;

@Data
public class TransactionRequestModel {

	private String transactionType;

	private String transactionDescription;

	private Double transactionAmount;

	private Long customerId;

}
