package com.olx.service;

// To implement this we add another class named LoginDelegateImpl
public interface LoginDelegate {
	
	public boolean isValidToken(String authToken);
}
