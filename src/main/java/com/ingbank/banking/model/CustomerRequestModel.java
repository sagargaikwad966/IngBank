package com.ingbank.banking.model;

import java.time.LocalDate;

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
public class CustomerRequestModel

{

	private String email;

	private String lastName;

	private String firstName;

	private LocalDate dateOfBirth;

	private String phoneNumber;

	private String panNumber;

	private String gender;

}
