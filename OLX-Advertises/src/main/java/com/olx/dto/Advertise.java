package com.olx.dto;
import java.time.LocalDate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Advertise DTO holding the advertise information")
public class Advertise {
	
	    @ApiModelProperty(value="Unqiue identifier for the advertise")
		private int id;
	    
	    @ApiModelProperty(value="Title of your advertisment")
		private String title;
	    
	    @ApiModelProperty(value="Price of your advertisment")
		private double price;
	    
	    
	    @ApiModelProperty(value="Description of your advertisment")
		private String description;
	    
	    @ApiModelProperty(value="Username of your advertisment")
		private String username;
	    
	    @ApiModelProperty(value="Created date of your advertisment")
		private LocalDate createdDate;
	    
	    @ApiModelProperty(value="Active status of your advertisement")
	    private String active;
	    
	    @ApiModelProperty(value="Name of the user who has posted the advertisement")
	    private String postedBy;
	    
	    @ApiModelProperty(value="Modified date of your advertisment")
		private LocalDate modifiedDate;		
	     
	   
	    @ApiModelProperty(value="Status id of your advertisment")
		private int statusId;	
	    
		@ApiModelProperty(value="Category of your advertisment")
	    private String category;
	   	
	    
	    @ApiModelProperty(value="Category id of your advertisment")
	    private int categoryId;
		
	    @ApiModelProperty(value="Status of your advertisment")
	    private String status;	
}
