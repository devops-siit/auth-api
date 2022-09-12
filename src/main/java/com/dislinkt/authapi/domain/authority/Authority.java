package com.dislinkt.authapi.domain.authority;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import com.dislinkt.authapi.domain.base.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Document
@Getter
@Setter
public class Authority extends BaseEntity implements GrantedAuthority{


	@Indexed(unique = true)
	private String role;

	@Override
	public String getAuthority() {
		
		return role;
	}

}