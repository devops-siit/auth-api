package com.dislinkt.authapi.repository;


import java.util.Optional;

import com.dislinkt.authapi.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

	Optional<Account> findOneByUsername(String username);

	Optional<Account> findOneByEmail(String email);
}
