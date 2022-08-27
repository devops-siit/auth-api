package com.dislinkt.authapi.domain.authority;


import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.springframework.security.core.GrantedAuthority;

import com.dislinkt.authapi.domain.base.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Authority extends BaseEntity implements GrantedAuthority{


	@Column(unique = true, nullable = false)
	private String role;

	@Override
	public String getAuthority() {
		
		return role;
	}

}