package com.dislinkt.authapi.web.rest.auth.payload;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;

    private String password;
}
