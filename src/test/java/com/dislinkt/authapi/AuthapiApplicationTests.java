package com.dislinkt.authapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@SpringBootTest
@TestExecutionListeners(
	    listeners = TransactionalTestExecutionListener.class,
	    inheritListeners = false
	    
)
class AuthapiApplicationTests {

	@Test
	void contextLoads() {
	}

}
