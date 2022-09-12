package com.dislinkt.authapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dislinkt.authapi.domain.authority.Authority;


public interface AuthorityRepository extends MongoRepository<Authority, Long>{
	
	Authority findByRole(String role);

}
