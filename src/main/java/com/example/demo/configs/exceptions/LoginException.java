package com.example.demo.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class LoginException implements Serializable {
    private static final long serialVersionUID = 8842760896219563389L;

    private final String message;
    private String email;
    private String password;

    public LoginException(String message) {
        this.message = message;
    }

}