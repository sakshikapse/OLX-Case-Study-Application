package com.olx.service;

import java.time.LocalDate;
import java.util.List;

import com.olx.dto.Advertise;
import com.olx.dto.AdvertiseRespDto;

public interface AdvertiseService {

	// Post new advertise service function
	// here we are also passing the authToken as the parameter
	
	public List<Advertise> getAllAdvertise();
	public Advertise createNewAdvertise(String authToken, Advertise advertise);
	public Advertise updateAdvertise(int id, Advertise advertise, String authToken);
	
	// Delete advertise by user id
    public boolean deleteAdvertiseById(int advertiseId, String authToken);
	
	public Advertise getAdvertiseById(String authToken, int advertiseId);
	
	public List<Advertise> getAllAdvertisesByUser(String authToken);
	
	public List<Advertise> getAdvertiseByFilter(String searchText, int category, int postedBy, String dateCondition,
			LocalDate onDate, LocalDate fromDate, LocalDate toDate, String sortBy, int startIndex, int records);
	
	

   public List<Advertise> getAdvertiseBySearch(String searchText);
   
   public Advertise getAdvertiseByUserById(String authToken, int id);
}
