package com.olx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("User DTO holding the user information")
public class User {
 
	@ApiModelProperty(value="Unqiue identifier for the user")
	private int id;
	
	@ApiModelProperty(value="Firstname of the user")
	private String firstname;
	
	@ApiModelProperty(value="Lastname of the user")
	private String lastname;
	
	@ApiModelProperty(value="Username of the user")
	private String username;
	
	@ApiModelProperty(value="Password of the user")
	private String password;
	
	@ApiModelProperty(value="Email of the user")
	private String email;
	
	@ApiModelProperty(value="Contact of the user")
	private String phone;
}
