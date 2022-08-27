package com.dislinkt.authapi.web.rest.user.payload;

import com.dislinkt.authapi.web.rest.base.BaseDTO;

import lombok.Data;

@Data
public class AuthorityDTO extends BaseDTO{
	
	private Long id;
	private String role;
	
	
	
}
