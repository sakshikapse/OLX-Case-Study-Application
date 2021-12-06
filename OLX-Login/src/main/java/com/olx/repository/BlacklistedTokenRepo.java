package com.olx.repository;

import javax.persistence.Id;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.olx.entity.BlacklistDocument;

public interface BlacklistedTokenRepo extends MongoRepository<BlacklistDocument, Id>{
	BlacklistDocument findByTokenEquals(String token);
}
