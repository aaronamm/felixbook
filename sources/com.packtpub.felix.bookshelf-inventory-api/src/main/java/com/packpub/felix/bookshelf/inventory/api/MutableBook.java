package com.packpub.felix.bookshelf.inventory.api;

public  interface MutableBook {

    void setIsbn(String isbn);

    void setTitle(String title);

    void setAuthor(String author);

    void setCategory(String category);

    void setRating(String rating);
}
