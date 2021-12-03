package com.olx.dto;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class AdvertiseRespDto {
	private int id;
	private String title;
	private String category;
	private String description;
	private String username;
	private Date createdDate;
	private Date modifiedDate;
	private String status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	@Override
	public String toString() {
		return "AdvertiseRespDto [id=" + id + ", title=" + title + ", category=" + category + ", description="
				+ description + ", username=" + username + ", createdDate=" + createdDate + ", modifiedDate="
				+ modifiedDate + ", status=" + status + "]";
	}
	
}
