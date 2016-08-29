package com.packtpub.felix.bookshelf.inventory.impl.mock;

import com.packpub.felix.bookshelf.inventory.api.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BookInventoryMockImpl implements BookInventory {
    public static final String DEFAULT_CATEGORY = "default";
    private Map<String, MutableBook> booksByISBN = new HashMap<String, MutableBook>();
    private Map<String, Integer> categories = new HashMap<String, Integer>();

    public Set<String> getCategories() {
        return this.categories.keySet();
    }

    public MutableBook createBook(String isbn) throws BookAlreadyExistsException {
        return new MutableBookImpl(isbn);
    }

    public MutableBook loadBookForEdit(String s) throws BookNotFoundException {
        return null;
    }

    public String storeBook(MutableBook book) throws InvalidBookException {
        String isbn = book.getIsbn();
        if (isbn == null) {
            throw new InvalidBookException("ISBN is not set");
        }

        this.booksByISBN.put(isbn, book);

        String category = book.getCategory();
        if (category == null) {
            category = DEFAULT_CATEGORY;
        }

        if (this.categories.containsKey(category)) {
            int count = this.categories.get(category);
            this.categories.put(category, count);
        } else {
            this.categories.put(category, 1);
        }
        return isbn;
    }

    public Book loadBook(String s) throws BookNotFoundException {
        return null;
    }

    public void removeBook(String s) throws BookNotFoundException {

    }

    public Set<String> searchBooks(Map<SearchCriteria, String> map) {
        return null;
    }
}
