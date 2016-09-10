package com.packtpub.felix.bookshelf.service.api;

public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException(String username) {
        super(String.format("invalid password for username %s",username));
    }
}
