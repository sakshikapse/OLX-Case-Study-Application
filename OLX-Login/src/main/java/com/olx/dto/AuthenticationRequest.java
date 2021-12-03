package com.olx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Authentication DTO holding the authentication information")
public class AuthenticationRequest {
	
	@ApiModelProperty(value="Username for the authentication")
	private String username;
	
	@ApiModelProperty(value="Password for the authentication")
	private String password;
}
