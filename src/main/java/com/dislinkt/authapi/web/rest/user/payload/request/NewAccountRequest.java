package com.dislinkt.authapi.web.rest.user.payload.request;

import lombok.Data;

@Data
public class NewAccountRequest {
	
	protected String firstName;

	protected String lastName;

	protected String username;
	
	protected String email;
	
	protected String password;
	
	protected String repeatedPass;

}
