package com.dislinkt.authapi.web.rest.auth.payload;


import com.dislinkt.authapi.web.rest.base.BaseDTO;
import lombok.Data;

@Data
public class AccountDTO extends BaseDTO {

	private String username;

	private String email;
}
