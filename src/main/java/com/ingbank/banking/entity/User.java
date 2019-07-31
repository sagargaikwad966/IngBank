package com.ingbank.banking.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable
{
	private static final long serialVersionUID = -6767153271912643724L;
	
	@Id
	private String userId;
	
	private String name;

}
