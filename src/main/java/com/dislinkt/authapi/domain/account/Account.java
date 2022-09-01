package com.dislinkt.authapi.domain.account;

import com.dislinkt.authapi.domain.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
public class Account extends BaseEntity {

    @NotNull
    @Size(max = 36)
    @Column(unique = true)
    private String username;

    @NotNull
    @Size(max = 128)
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(max = 60)
    @NotNull
    protected String password;
}
