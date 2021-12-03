package com.olx.service;

import com.olx.dto.User;

public interface UserService {
	
	public User createNewUser(User user);
	public User getUser(String authToken);	
	public boolean LogoutUserByAuthtoken(String authToken);
}
