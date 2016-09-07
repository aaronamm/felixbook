package com.packtpub.felix.bookshelf.inventory.api;

public class InvalidBookException extends Exception {

    public InvalidBookException(String message){
        super(message);
    }
}
