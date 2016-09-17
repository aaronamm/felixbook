package com.packtpub.felix.bookshelf.service.tui;

import com.packtpub.felix.bookshelf.inventory.api.Book;
import com.packtpub.felix.bookshelf.inventory.api.BookNotFoundException;
import com.packtpub.felix.bookshelf.service.api.BookInventoryNotRegisteredRuntimeException;
import com.packtpub.felix.bookshelf.service.api.InvalidCredentialsException;
import com.packtpub.felix.bookshelf.service.api.SessionNotValidRuntimeException;
import org.apache.felix.service.command.Descriptor;

import java.util.Set;

/**
 * Created by aaron on 9/17/2016.
 */
public interface BookshelfServiceProxy {
    String SCOPE = "book";
    String[] FUNCTIONS = new String[]{
            "search"
    };

    Set<Book> search(
             String username,
             String password,
             String attribute,
             String filter)
            throws InvalidCredentialsException,
            BookInventoryNotRegisteredRuntimeException,
            SessionNotValidRuntimeException, BookNotFoundException;

    Set<Book> search(
            String username,
            String password,
            String attribute,
            int lower,
            int upper)
            throws InvalidCredentialsException,
            BookInventoryNotRegisteredRuntimeException, SessionNotValidRuntimeException, BookNotFoundException;
}
