package com.dislinkt.authapi.web.rest.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dislinkt.authapi.domain.user.User;
import com.dislinkt.authapi.security.TokenUtils;
import com.dislinkt.authapi.security.auth.TokenBasedAuthentication;
import com.dislinkt.authapi.service.user.UserService;
import com.dislinkt.authapi.web.rest.user.payload.UserDTO;


@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserResource {
	
	@Autowired 
	private UserService service;
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
		UserDTO u = service.findOne(id);
		if (u == null) {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(u, HttpStatus.OK);
	}
	
	@RequestMapping(value="/current-user/{authToken}", method=RequestMethod.GET)
    public ResponseEntity<UserDTO> getCurrentUser(@PathVariable String authToken){ 
		
		UserDTO currentUser = service.getCurrentUserFromToken(authToken);
		
        if (currentUser == null)
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{email:.+}", method = RequestMethod.GET,produces=MediaType.ALL_VALUE, consumes=MediaType.ALL_VALUE)
	public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email){
		
		UserDTO u = service.findByEmail(email.toString());
		if (u == null) {
	
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(u, HttpStatus.OK);
	}

	
}
