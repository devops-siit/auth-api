package com.dislinkt.authapi.event;

import com.dislinkt.authapi.domain.account.Gender;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccountCreatedEvent {

    private String uuid;

    private String username;

    private String name;

    private String email;

    private String phone;

    private Gender gender;

    private LocalDateTime dateOfBirth;
}
