package com.olx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.olx.entity.StatusEntity;

public interface StatusRepository extends JpaRepository<StatusEntity, Integer> {
	@Query(value= "SELECT s.status from adv_status s WHERE s.id =:statusId",nativeQuery=true)
	String findByStatusId(int statusId);
}
