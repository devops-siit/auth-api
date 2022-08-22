package com.dislinkt.authapi.web.rest.user.payload;


import java.util.Set;

public class UserDTO {
	
	protected Long id;
	protected String firstName;
	protected String lastName;
	protected String username;
	protected String password;
	protected Boolean active;
	protected Boolean verified;
	   
	
	
	public UserDTO() {
		super();
	}
	
	public UserDTO(Long id, String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.id = id;
	}
	public UserDTO(String firstName, String lastName, String username, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
	}
	public UserDTO(Long id, String firstName, String lastName, String username, String password, Boolean active, Boolean verified) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.active = active;
		this.verified = verified;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return username;
	}

	public void setEmail(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}



	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
				+ ", password=" + password + ", active=" + active + ", verified=" + verified  + "]";
	}
	
	
}
