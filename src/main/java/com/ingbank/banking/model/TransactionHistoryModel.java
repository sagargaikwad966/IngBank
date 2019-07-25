package com.ingbank.banking.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TransactionHistoryModel {

	private Long transactionId;

	private LocalDateTime transactionDateTime;

	private String transactionType;

	private String transactionDescription;

	private Double transactionAmount;

	private Double balance;

}
