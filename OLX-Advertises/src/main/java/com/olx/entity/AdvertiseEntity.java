package com.olx.entity;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "advertises")
public class AdvertiseEntity {

	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
		private int id;
	    
	    @Column(name="title")
		private String title;
	    
	    @Column(name="price")
		private double price;
	    
	    @Column(name="category")
		private int categoryId;
	    
	    @Column(name="description")
		private String description;
	    
	    @Column(name="username")
		private String username;
	    
	    @Column(name="status")
		private int statusId;

	    @Column(name="created_date")
	    private LocalDate createdDate;
	    
	    @Column(name="modified_date")
	    private LocalDate modifiedDate;

	    @Column(name="active")
	    private String active;
	    
	    
	    @Column(name="postedBy")
	    private String postedBy;


		@Override
		public String toString() {
			return "AdvertiseEntity [id=" + id + ", title=" + title + ", price=" + price + ", categoryId=" + categoryId
					+ ", description=" + description + ", username=" + username + ", statusId=" + statusId
					+ ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", active=" + active
					+ ", postedBy=" + postedBy + "]";
		}


		public AdvertiseEntity(int id, String title, double price, int categoryId, String description, String username,
				int statusId, LocalDate createdDate, LocalDate modifiedDate, String active, String postedBy) {
			super();
			this.id = id;
			this.title = title;
			this.price = price;
			this.categoryId = categoryId;
			this.description = description;
			this.username = username;
			this.statusId = statusId;
			this.createdDate = createdDate;
			this.modifiedDate = modifiedDate;
			this.active = active;
			this.postedBy = postedBy;
		}


		public AdvertiseEntity() {
			super();
		}

}
