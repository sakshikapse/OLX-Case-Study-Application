package com.olx.entity;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="BlacklistToken")
public class BlacklistDocument {

	@Id
	private Id _id;
	private String Token;
	
	
	@Override
	public String toString() {
		return "BlacklistDocument [_id=" + _id + ", Token=" + Token + "]";
	}


	public BlacklistDocument(Id _id, String token) {
		super();
		this._id = _id;
		Token = token;
	}


	public BlacklistDocument() {
		super();
	}


	public Id get_id() {
		return _id;
	}


	public void set_id(Id _id) {
		this._id = _id;
	}


	public String getToken() {
		return Token;
	}


	public void setToken(String token) {
		Token = token;
	}
	
}
