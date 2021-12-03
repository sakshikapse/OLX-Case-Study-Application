package com.olx.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.olx.dto.Category;
import com.olx.dto.Status;
import com.olx.exception.RecordNotFoundException;
import com.olx.repository.CategoryRepository;
import com.olx.repository.StatusRepository;
import com.olx.service.MasterDataService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("olx")
public class MasterDataController  {

	@Autowired
	private MasterDataService masterDataService;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private StatusRepository statusRepository;
	
	
	// 5 API - Return all advertisement categories
	@GetMapping(value="/advertise/category", 
			    produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value ="This REST endpoint returns all advertise category")
	public ResponseEntity <List <Category>> getAllCategories(){
		
		 List <Category> category = this.masterDataService.getAllCategories();
		 if(category.size()<=0) {
			 // return new ResponseEntity(HttpStatus.NOT_FOUND);
			 throw new RecordNotFoundException();
		 }else {
			 return new ResponseEntity(category, HttpStatus.OK);
		 }
	}
	
		
	

	// 6 API - Return all possible advertise status
	@GetMapping(value="/advertise/status",
			    produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value ="This REST endpoint returns all advertise status")
	public ResponseEntity <List<Status>> getAllStatus(){
		
		List <Status> status = this.masterDataService.getAllStatus();
		if(status.size()<=0) {
			 // return new ResponseEntity(HttpStatus.NOT_FOUND);
			throw new RecordNotFoundException();
		}else {
			 return new ResponseEntity(status, HttpStatus.OK);
		 }
	}
	
	
	
	
	// Writing function from the MasterDataDelegate that we are looking for status by id
	@GetMapping(value="/advertise/status/{id}")
	@ApiOperation(value ="This REST Template function from MasterDataDelegate that we are looking status name by id")
	public String getStatusById(@PathVariable("id") int statusId) {
		return this.masterDataService.findStatusById(statusId);
	}
	
	
	@GetMapping(value="/advertise/category/{id}")
	@ApiOperation(value ="This REST Template function from MasterDataDelegate that we are looking category name by id")
	public String getCategoryById(@PathVariable("id") int categoryId) {
		return this.masterDataService.findCategoryById(categoryId);
	}	
}		

