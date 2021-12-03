package com.olx.dto;

import lombok.Data;

@Data
public class User {
 
	private int id;
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private String email;
	private String phone;
}
