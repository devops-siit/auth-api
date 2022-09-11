package com.dislinkt.authapi.web.rest.auth;

import com.dislinkt.authapi.service.auth.AuthService;
import com.dislinkt.authapi.util.ReturnResponse;
import com.dislinkt.authapi.web.rest.auth.payload.AccountDTO;
import com.dislinkt.authapi.web.rest.auth.payload.JWTTokenDTO;
import com.dislinkt.authapi.web.rest.auth.payload.LoginRequest;
import com.dislinkt.authapi.web.rest.auth.payload.NewAccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthResource {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JWTTokenDTO> login(@RequestBody LoginRequest request) {
        return ReturnResponse.entityGet(authService.login(request));
    }

    @GetMapping("/validate-token")
    public ResponseEntity<AccountDTO> validateToken(@RequestParam String token) {
        return ReturnResponse.entityGet(authService.validateToken(token));
    }
    @PostMapping("/register")
    public ResponseEntity<JWTTokenDTO> register(@RequestBody NewAccountRequest request) {
        return ReturnResponse.entityGet(authService.register(request));
    }
}
