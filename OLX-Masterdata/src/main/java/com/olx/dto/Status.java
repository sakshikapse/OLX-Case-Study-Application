package com.olx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Status DTO holding the status information")
public class Status {
	
	@ApiModelProperty(value="Unqiue identifier for the status")
	private int id;
	
	@ApiModelProperty(value="Status of your advertisment")
	private String status;
}
