package com.dislinkt.authapi.web.rest.user.payload;


import java.util.Set;

import com.dislinkt.authapi.web.rest.base.BaseDTO;

import lombok.Data;

@Data
public class UserDTO extends BaseDTO {
	
	protected Long id;
	protected String firstName;
	protected String lastName;
	protected String username;
	protected String email;
	//protected String password;
	protected Boolean active;
	protected Boolean verified;
	   

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
				+ ", active=" + active + ", verified=" + verified  + "]";
	}
	
	
}
