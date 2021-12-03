package com.olx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.olx.entity.AdvertiseEntity;

public interface AdvertiseRepository extends JpaRepository<AdvertiseEntity, Integer>{
	
	
	@Query(value="SELECT * from advertises WHERE title LIKE %:searchText% or category LIKE %:searchText% or description LIKE %:searchText% or username LIKE %:searchText% ",nativeQuery=true)
	List<AdvertiseEntity> findBySearch(String searchText);
	
	
	@Query(value="SELECT COUNT(*) from advertises ",nativeQuery = true)
	public long findByCount();

	@Query(value="SELECT COUNT(*) from advertises WHERE active = '1' ",nativeQuery = true)
	public long findActiveCount();
	
	@Query(value="SELECT COUNT(*) from advertises WHERE active = '0' ",nativeQuery = true)
	public long findClosedCount();
	
	@Query(value="SELECT COUNT(*) from advertises WHERE active = '1' ",nativeQuery = true)
	public long findOpenCount();
}
