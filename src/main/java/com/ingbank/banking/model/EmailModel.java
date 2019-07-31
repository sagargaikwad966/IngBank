package com.ingbank.banking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailModel 
{
	private String mailTo;
	private String mailSubject;
	private String mailBody;
	

}
