package com.dislinkt.authapi.service;

import static com.dislinkt.authapi.constants.AccountConstants.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.dislinkt.authapi.config.MongoConfig;
import com.dislinkt.authapi.domain.account.Account;
import com.dislinkt.authapi.event.AccountRegistrationSource;
import com.dislinkt.authapi.repository.AccountRepository;
import com.dislinkt.authapi.service.auth.AuthService;
import com.dislinkt.authapi.web.rest.auth.payload.AccountDTO;
import com.dislinkt.authapi.web.rest.auth.payload.JWTTokenDTO;
import com.dislinkt.authapi.web.rest.auth.payload.LoginRequest;
import com.dislinkt.authapi.web.rest.auth.payload.NewAccountRequest;


@RunWith(SpringRunner.class)
@DataMongoTest
@TestExecutionListeners(
	    listeners = {TransactionalTestExecutionListener.class, DependencyInjectionTestExecutionListener.class},
	    inheritListeners = false    
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ComponentScan
@Import(MongoConfig.class)
public class AuthServiceIntegrationTest {

	@Autowired
	@InjectMocks
	private AuthService service;
	
	@Mock
	private AccountRegistrationSource accountRegistrationSource;
	
	@Autowired
	private AccountRepository repository;
	
	@BeforeAll
	public void initDatabase() {

		MockitoAnnotations.initMocks(this);
		Mockito.when(accountRegistrationSource.accountRegistration()).thenReturn(new MessageChannel() {
			
			@Override
			public boolean send(Message<?> message, long timeout) {
				
				return true;
			}
		});
		Account acc = new Account();
		acc.setEmail(DB_ACCOUNT_EMAIL);
		acc.setPassword(DB_ACCOUNT_PASSWORD); 
		acc.setUsername(DB_ACCOUNT_USERNAME);
		
		repository.save(acc);
	}
	
	@AfterAll
	public void emptyDataBase() {
		
		repository.deleteAll();
	}
	
	@Test
	@Rollback(true)
	public void testRegistration() {
		NewAccountRequest req = new NewAccountRequest();
		req.setDateOfBirth(NEW_ACCOUNT_DATE_OF_BIRTH);
		req.setEmail(NEW_ACCOUNT_EMAIL);
		req.setGender(NEW_ACCOUNT_GENDER);
		req.setName(NEW_ACCOUNT_NAME);
		req.setPassword(NEW_ACCOUNT_PASSWORD);
		req.setPhone(NEW_ACCOUNT_PHONE);
		req.setUsername(NEW_ACCOUNT_USERNAME);
		
		JWTTokenDTO token = service.register(req);
		assertNotNull(token);
	}
	
	@Test
	public void testLogin() {
		LoginRequest req = new LoginRequest();
		req.setUsername(DB_ACCOUNT_USERNAME);
		req.setPassword("asdf");
		JWTTokenDTO token = service.login(req);
		assertNotNull(token);
	}
	
	@Test
	public void testLoginIncorrectPassword() {
		Throwable exception = assertThrows(
	            Exception.class, () -> {
	            	LoginRequest req = new LoginRequest();
	        		req.setUsername(DB_ACCOUNT_USERNAME);
	        		req.setPassword("blabla");
	        		service.login(req);
	            }
	    );
	    assertEquals("Password not valid", exception.getMessage());
		
	}
	
	@Test
	public void testFindOneByUsername() {
		Account found = service.findOneByUsernameOrThrowException(DB_ACCOUNT_USERNAME);
		
		assertEquals(DB_ACCOUNT_USERNAME, found.getUsername());
	}
	
	@Test
	public void testFindOneByUsernameException() {
		Throwable exception = assertThrows(
	            Exception.class, () -> {
	            	service.findOneByUsernameOrThrowException(DB_UNKNOWN_USERNAME);
	            }
	    );
	    assertEquals("Account not found", exception.getMessage());
		
	}
	@Test
	public void testCheckIfAccountExists() {
		boolean exists = service.checkIfAccountExists(DB_ACCOUNT_USERNAME, DB_ACCOUNT_EMAIL);
		
		assertTrue(exists);
	}
	
	@Test
	public void testCheckIfAccountDoenstExist() {
		boolean exists = service.checkIfAccountExists(DB_UNKNOWN_USERNAME, DB_UNKNOWN_EMAIL);
		
		assertFalse(exists);
	}
	
}
