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
    String FUNCTIONS =   "[search,add]";

    Set<Book> search(String username, String password, String attribute, String filter);

    Set<Book> search(String username, String password, String attribute, int lower, int upper);

    String add(String username, String password, String isbn, String title, String author, String category, int rating);

}
