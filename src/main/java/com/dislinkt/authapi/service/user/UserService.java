package com.dislinkt.authapi.service.user;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dislinkt.authapi.domain.user.User;
import com.dislinkt.authapi.repository.UserRepository;
import com.dislinkt.authapi.security.TokenUtils;
import com.dislinkt.authapi.service.authority.AuthorityService;
import com.dislinkt.authapi.web.rest.user.payload.UserDTO;




@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
    private TokenUtils tokenUtils;
	
	@Autowired
	private UserDetailsService userDetailsService;

	public List<User> findAll() {
		return repository.findAllByActive(true);
	}

	public UserDTO findOne(Long id) {
		User found = repository.findByIdAndActive(id, true).orElse(null);
		UserDTO dto = new UserDTO();
		if (found != null) {
			dto.setActive(found.getActive());
			dto.setEmail(found.getEmail());
			dto.setFirstName(found.getFirstName());
			dto.setLastName(found.getLastName());
			dto.setId(found.getId());
			dto.setPassword(found.getPassword());
		}
		return dto;
	}

	public UserDTO findByEmail(String email) {
		
		User found = repository.findByEmailAndActive(email, true);
		UserDTO dto = new UserDTO();
		if (found != null) {
			dto.setActive(found.getActive());
			dto.setEmail(found.getEmail());
			dto.setFirstName(found.getFirstName());
			dto.setLastName(found.getLastName());
			dto.setId(found.getId());
			dto.setPassword(found.getPassword());
		}
		return dto;
		
	}

	public UserDTO create(User entity) throws Exception {
		//Ovo se ne radi ovde
//		if (repository.findByEmail(entity.getEmail()) != null) {
//			throw new Exception("User with given email address already exists");
//		}
//		RegisteredUser u = new RegisteredUser();
//		// u.setUsername(entity.getUsername());
//		// pre nego sto postavimo lozinku u atribut hesiramo je
//		u.setPassword(passwordEncoder.encode(entity.getPassword()));
//		u.setFirstName(entity.getFirstName());
//		u.setLastName(entity.getLastName());
//		u.setEmail(entity.getEmail());
//		u.setActive(true);
//		u.setVerified(false);
//
//		Set<Authority> auth = authService.findByName("ROLE_REGISTERED_USER");
//		// u primeru se registruju samo obicni korisnici i u skladu sa tim im se i
//		// dodeljuje samo rola USER
//		u.setAuthority(auth);
//
//		u = this.repository.save(u);
		return null;
	}

	

	public User update(User entity, Long id) throws Exception {
		return null;
	}

	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub

	}

	public Page<User> findAll(Pageable pageable) {
		return repository.findByActive(pageable, true);
	}

	public UserDTO getCurrentUserFromToken(String authToken) {
		
		if (authToken != null) {
            // uzmi username iz tokena (username = email u nasem slucaju)
            String username = tokenUtils.getUsernameFromToken(authToken);
            if (username != null) {
            	
            	UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // proveri da li je prosledjeni token validan
                if (tokenUtils.validateToken(authToken, userDetails)) {
                	UserDTO dbUser = findByEmail(username);
            		return dbUser;
                } 
            }
        }
		return null;
	}

//	public void save(User user) {
//		// TODO Auto-generated method stub
//		
//	}

}
