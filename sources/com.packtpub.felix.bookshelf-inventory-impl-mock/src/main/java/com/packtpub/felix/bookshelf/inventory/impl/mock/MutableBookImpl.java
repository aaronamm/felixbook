package com.packtpub.felix.bookshelf.inventory.impl.mock;

import com.packtpub.felix.bookshelf.inventory.api.MutableBook;

public class MutableBookImpl implements MutableBook {

    private String isbn;
    private String author;
    private String title;
    private String category;
    private int rating;

    public MutableBookImpl(String isbn) {
        setIsbn(isbn);
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setRating(int rating) {
       this.setRating(rating);
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return  title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public int getRating() {
        return rating;
    }

    public String toString(){
       StringBuffer buf = new StringBuffer() ;
        buf.append(getCategory()).append(": ");
        buf.append(getTitle()).append(" from ").append(getAuthor());
        buf.append(" [").append(getRating()).append("] ");
        return buf.toString();
    }
}
