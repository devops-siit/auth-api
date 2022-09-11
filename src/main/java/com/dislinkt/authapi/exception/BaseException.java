package com.dislinkt.authapi.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BaseException extends RuntimeException {

    private HttpStatus status;

    public BaseException(String errorMessage, HttpStatus status) {
        super(errorMessage);
        this.status = status;
    }
}