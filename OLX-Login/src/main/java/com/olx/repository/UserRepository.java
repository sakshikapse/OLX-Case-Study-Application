package com.olx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.olx.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{
	@Query("from UserEntity where username=:username")
	UserEntity findBySingleUsername(@Param("username") String username);
	List<UserEntity> findByUsername(String username);
}
