package com.dislinkt.authapi.domain.account;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.dislinkt.authapi.domain.base.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class Account extends BaseEntity {

	@Transient
    public static final String SEQUENCE_NAME = "account_sequence";
	
    @NotNull
    @Size(max = 36)
    @Indexed(unique = true)
    private String username;

    @NotNull
    @Size(max = 128)
    @Email
    @Indexed(unique = true)
    private String email;

    @NotBlank
    @Size(max = 60)
    @NotNull
    protected String password;
}
