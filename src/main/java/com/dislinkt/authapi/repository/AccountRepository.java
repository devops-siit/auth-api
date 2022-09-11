package com.dislinkt.authapi.repository;


import java.util.Optional;

import com.dislinkt.authapi.domain.account.Account;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface AccountRepository extends MongoRepository<Account, Long>{

	Optional<Account> findOneByUsername(String username);

	Optional<Account> findOneByEmail(String email);
}
