package com.dislinkt.authapi.web.rest.authentication;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dislinkt.authapi.domain.user.User;
import com.dislinkt.authapi.repository.UserRepository;
import com.dislinkt.authapi.security.TokenUtils;
import com.dislinkt.authapi.service.customuserdetails.CustomUserDetailsService;
import com.dislinkt.authapi.web.rest.user.payload.UserDTO;
import com.dislinkt.authapi.web.rest.user.payload.UserLoginDTO;
import com.dislinkt.authapi.web.rest.user.payload.UserTokenStateDTO;


//Kontroler zaduzen za autentifikaciju korisnika
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationResource {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    
    @RequestMapping(value = "/log-in", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserLoginDTO authenticationRequest,
                                                                    HttpServletResponse response) {
  
        User user = userDetailsService.usernamePasswordAuthentication(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        if(user != null) {
        	String jwt = tokenUtils.generateToken(user); 
            int expiresIn = tokenUtils.getExpiredIn();
                    
            // Vrati token kao odgovor na uspesnu autentifikaciju
            return ResponseEntity.ok(new UserTokenStateDTO(jwt, expiresIn));
        }
       
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    
	// U slucaju isteka vazenja JWT tokena, endpoint koji se poziva da se token osvezi
    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public ResponseEntity<UserTokenStateDTO> refreshAuthenticationToken(HttpServletRequest request) {
        String token = tokenUtils.getToken(request);
        String username = this.tokenUtils.getUsernameFromToken(token);
        User user = (User) this.userDetailsService.loadUserByUsername(username);

        if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = tokenUtils.refreshToken(token);
            int expiresIn = tokenUtils.getExpiredIn();

            return ResponseEntity.ok(new UserTokenStateDTO(refreshedToken, expiresIn));
        } else {
            UserTokenStateDTO userTokenState = new UserTokenStateDTO();
            return ResponseEntity.badRequest().body(userTokenState);
        }
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
    
    	Map<String, String> result = new HashMap<>();
    	try {
    		userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
            result.put("result", "success");
           
    	}
    	catch(Exception e) {
    		
    		return ResponseEntity.badRequest().body(result);
    	}
    	return ResponseEntity.accepted().body(result);
    }
    
    @RequestMapping(value = "/sign-out", method = RequestMethod.GET)
    public ResponseEntity<?> signOut() throws Exception {   	 
    	SecurityContextHolder.getContext().setAuthentication(null);
        return new ResponseEntity<>(HttpStatus.OK); 
    }

    static class PasswordChanger {
        public String oldPassword;
        public String newPassword;
    }

    public AuthenticationResource() {
        
      
    }
    
}
