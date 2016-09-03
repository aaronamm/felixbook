package com.packpub.felix.bookshelf.inventory.api;

public class BookNotFoundException extends Exception {

    public BookNotFoundException(String isbn) {
        super(String.format("no book with isbn %s", isbn));
    }
}
