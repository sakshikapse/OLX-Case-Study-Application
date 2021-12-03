package com.olx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Category DTO holding the category information")
public class Category {
	
	@ApiModelProperty(value="Unqiue identifier for the category")
	private int id;
	
	@ApiModelProperty(value="Category name of your advertisment")
	private String categoryName;
	
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
