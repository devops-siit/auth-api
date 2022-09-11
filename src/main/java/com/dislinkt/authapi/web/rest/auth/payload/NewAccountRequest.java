package com.dislinkt.authapi.web.rest.auth.payload;

import com.dislinkt.authapi.domain.account.Gender;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewAccountRequest {

	private String username;

	private String name;

	private String email;

	private String phone;

	private Gender gender;

	private String password;

	private LocalDateTime dateOfBirth;
}
