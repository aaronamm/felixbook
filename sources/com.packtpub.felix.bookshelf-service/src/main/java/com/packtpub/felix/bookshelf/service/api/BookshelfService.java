package com.packtpub.felix.bookshelf.service.api;

import com.packtpub.felix.bookshelf.inventory.api.*;
import com.packtpub.felix.bookshelf.service.impl.BookInventoryNotRegisteredRuntimeException;
import com.packtpub.felix.bookshelf.service.impl.SessionNotValidRuntimeException;

import java.util.Set;

public interface BookshelfService extends Authentication {
    Set<String> getGroups(String sessionId) throws SessionNotValidRuntimeException, BookInventoryNotRegisteredRuntimeException;

    void addBook(String session, String isbn, String title,
                 String author, String category, int rating)
            throws BookAlreadyExistsException, InvalidBookException, SessionNotValidRuntimeException, BookInventoryNotRegisteredRuntimeException;

    void modifyBookCategory(
            String session, String isbn, String category)
            throws BookNotFoundException, InvalidBookException, SessionNotValidRuntimeException, BookInventoryNotRegisteredRuntimeException;

    void modifyBookRating(String session, String isbn, int rating)
            throws BookNotFoundException, InvalidBookException, SessionNotValidRuntimeException, BookInventoryNotRegisteredRuntimeException;

    void removeBook(String session, String isbn)
            throws BookNotFoundException, SessionNotValidRuntimeException, BookInventoryNotRegisteredRuntimeException;

    Book getBook(String session, String isbn)
            throws BookNotFoundException, SessionNotValidRuntimeException, BookInventoryNotRegisteredRuntimeException;

     MutableBook getBookForEdit(String sessionId, String isbn) throws BookNotFoundException, SessionNotValidRuntimeException, BookInventoryNotRegisteredRuntimeException;


    Set<String> searchBooksByCategory(
            String session, String categoryLike) throws SessionNotValidRuntimeException, BookInventoryNotRegisteredRuntimeException;

    Set<String> searchBooksByAuthor(
            String session, String authorLike) throws SessionNotValidRuntimeException, BookInventoryNotRegisteredRuntimeException;

    Set<String> searchBooksByTitle(
            String session, String titleLike) throws BookInventoryNotRegisteredRuntimeException, SessionNotValidRuntimeException;

    Set<String> searchBooksByRating(
            String session, int ratingLower, int ratingUpper) throws SessionNotValidRuntimeException, BookInventoryNotRegisteredRuntimeException;
}
