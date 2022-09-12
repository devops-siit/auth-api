package com.dislinkt.authapi.service.auth;

import com.dislinkt.authapi.domain.account.Account;
import com.dislinkt.authapi.event.AccountCreatedEvent;
import com.dislinkt.authapi.event.AccountRegistrationSource;
import com.dislinkt.authapi.exception.types.EntityAlreadyExistsException;
import com.dislinkt.authapi.exception.types.EntityNotFoundException;
import com.dislinkt.authapi.repository.AccountRepository;
import com.dislinkt.authapi.service.generator.SequenceGeneratorService;
import com.dislinkt.authapi.web.rest.auth.payload.AccountDTO;
import com.dislinkt.authapi.web.rest.auth.payload.JWTTokenDTO;
import com.dislinkt.authapi.web.rest.auth.payload.LoginRequest;
import com.dislinkt.authapi.web.rest.auth.payload.NewAccountRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
@EnableBinding(AccountRegistrationSource.class)
public class AuthService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private SequenceGeneratorService sequenceGenerator;

    @Autowired
    private AccountRegistrationSource accountRegistrationSource;

    public JWTTokenDTO login(LoginRequest request) {
        Account account = findOneByUsernameOrThrowException(request.getUsername());

        if (passwordEncoder.matches(CharBuffer.wrap(request.getPassword()), account.getPassword())) {
            JWTTokenDTO dto = new JWTTokenDTO();
            dto.setToken(createToken(account.getUsername()));
            return dto;
        }

        throw new EntityNotFoundException("Password not valid");
    }

    public JWTTokenDTO register(NewAccountRequest request) {
        boolean accountExists = checkIfAccountExists(request.getUsername(), request.getEmail());

        if (accountExists) {
            throw new EntityAlreadyExistsException("Account already exists");
        }

        Account account = new Account();
        account.setUsername(request.getUsername());
        account.setEmail(request.getEmail());
        account.setPassword(passwordEncoder.encode(request.getPassword()));        
        account.setId(sequenceGenerator.generateSequence(Account.SEQUENCE_NAME));
        accountRepository.insert(account);

        AccountCreatedEvent event = new AccountCreatedEvent();
        event.setUuid(account.getUuid());
        event.setUsername(account.getUsername());
        event.setName(request.getName());
        event.setEmail(request.getEmail());
        event.setGender(request.getGender());
        event.setPhone(request.getPhone());
        event.setDateOfBirth(request.getDateOfBirth());

        accountRegistrationSource.accountRegistration().send(MessageBuilder.withPayload(event).build());

        JWTTokenDTO dto = new JWTTokenDTO();
        dto.setToken(createToken(request.getUsername()));

        return dto;
    }

    public AccountDTO validateToken(String token) {
        try {
            String username = Jwts.parser()
                    .setSigningKey("qvuSsmEgb8")
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            Account account = findOneByUsernameOrThrowException(username);

            AccountDTO dto = new AccountDTO();
            dto.setEmail(account.getEmail());
            dto.setUsername(account.getUsername());
            dto.setUuid(account.getUuid());

            return dto;
        } catch (Exception ex) {
            throw new EntityNotFoundException("Unauthorized");
        }
    }

    private String createToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);

        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hour

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, "qvuSsmEgb8")
                .compact();
    }

    public Account findOneByUsernameOrThrowException(String username) {
        return accountRepository.findOneByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
    }

    public boolean checkIfAccountExists(String username, String email) {
        return accountRepository.findOneByUsername(username).isPresent()
                && accountRepository.findOneByEmail(email).isPresent();
    }

	public Set<AccountDTO> getAll() {
		Set<AccountDTO> retVal =new HashSet<AccountDTO>();
		List<Account> found = accountRepository.findAll();
		for(Account a: found) {
			AccountDTO dto = new AccountDTO();
			dto.setUsername(a.getUsername());
			dto.setEmail(a.getEmail());
			dto.setUuid(a.getUuid());
			retVal.add(dto);
		}
		return retVal;
		
	}
}
