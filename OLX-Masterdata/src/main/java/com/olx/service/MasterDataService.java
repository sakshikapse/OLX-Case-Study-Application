package com.olx.service;

import java.util.List;

import com.olx.dto.Category;
import com.olx.dto.Status;

public interface MasterDataService {

	public List<Category> getAllCategories();
	
	public List<Status> getAllStatus();

	public String findCategoryById(int categoryId);
	
	public String findStatusById(int statusId);

}
