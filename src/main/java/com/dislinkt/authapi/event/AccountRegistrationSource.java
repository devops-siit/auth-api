package com.dislinkt.authapi.event;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface AccountRegistrationSource {

    @Output("accountRegistrationChannel")
    MessageChannel accountRegistration();
}