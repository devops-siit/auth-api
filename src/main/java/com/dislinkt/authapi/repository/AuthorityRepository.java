package com.dislinkt.authapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dislinkt.authapi.domain.authority.Authority;



@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long>{
	Authority findByRole(String role);

}
