package com.olx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="adv_status")
public class StatusEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int Id;
	
	@Column(name="status")
	private String status;

	@Override
	public String toString() {
		return "StatusEntity [Id=" + Id + ", status=" + status + "]";
	}

	public StatusEntity(int id, String status) {
		super();
		Id = id;
		this.status = status;
	}

	public StatusEntity() {
		super();
	}
	
	
	
}
