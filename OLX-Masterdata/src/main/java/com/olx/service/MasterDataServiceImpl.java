package com.olx.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olx.dto.Category;
import com.olx.dto.Status;
import com.olx.entity.CategoryEntity;
import com.olx.entity.StatusEntity;
import com.olx.repository.CategoryRepository;
import com.olx.repository.StatusRepository;

@Service 
public class MasterDataServiceImpl implements MasterDataService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private StatusRepository statusRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<Category> getAllCategories() {
		List<CategoryEntity> categoriesEntities = categoryRepository.findAll();
		List<Category> categoriesDtoList = new ArrayList<Category>();
		
		TypeMap<CategoryEntity, Category> typeMap = this.modelMapper.typeMap(CategoryEntity.class, Category.class);
	    typeMap.addMappings((mapper) -> {
	    	mapper.map(source->source.getCategory(), Category::setCategoryName);
	    	// mapper.map(source->source.getId(), Category::setCategoryId);
	    });
		
		for(CategoryEntity categoryEntity: categoriesEntities) {			
			Category categoryDto = this.modelMapper.map(categoryEntity, Category.class);
			categoriesDtoList.add(categoryDto);
		}
		return categoriesDtoList;
	
	}



	@Override
	public List<Status> getAllStatus() {
		List<StatusEntity> statusEntities = statusRepository.findAll();
		List<Status> statusDtoList = new ArrayList<Status>();
		for(StatusEntity statusEntity: statusEntities) {
			Status statusDto = new Status();
			statusDto.setId(statusEntity.getId());
			statusDto.setStatus(statusEntity.getStatus());
			statusDtoList.add(statusDto);
		}
		// Returning statusDtoList
		return statusDtoList;
	}

	@Override
	public String findCategoryById(int categoryId) {
		String categoryName = this.categoryRepository.findByCategoryId(categoryId);
		return categoryName;
	}

	@Override
	public String findStatusById(int statusId){
		String statusName = this.statusRepository.findByStatusId(statusId);
		return statusName;
	}
}
