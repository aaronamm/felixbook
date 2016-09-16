package com.packtpub.felix.bookshelf.service.tui;

import com.packtpub.felix.bookshelf.inventory.api.Book;
import com.packtpub.felix.bookshelf.inventory.api.BookNotFoundException;
import com.packtpub.felix.bookshelf.service.api.BookshelfService;
import com.packtpub.felix.bookshelf.service.api.InvalidCredentialsException;
import com.packtpub.felix.bookshelf.service.impl.BookInventoryNotRegisteredRuntimeException;
import com.packtpub.felix.bookshelf.service.impl.SessionNotValidRuntimeException;
import org.apache.felix.service.command.Descriptor;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import java.util.HashSet;
import java.util.Set;

public class BookshelfServiceProxy {
    public static final String SCOPE = "book";
    public static final String[] FUNCTIONS = new String[]{
            "search"
    };
    private BundleContext context;

    public BookshelfServiceProxy(BundleContext context) {
        this.context = context;
    }

    @Descriptor("Search books by author, title, or category")
    public Set<Book> search(
            @Descriptor("username") String username,
            @Descriptor("password") String password,
            @Descriptor("search on attribute: author, title, or category") String attribute,
            @Descriptor("match like (use % at the beginning or end of <like>" +
                    " for wild-card)") String filter)
            throws InvalidCredentialsException,
            BookInventoryNotRegisteredRuntimeException,
            SessionNotValidRuntimeException, BookNotFoundException {

        BookshelfService service = lookupService();
        String sessionId = service.login(username, password.toCharArray());

        Set<String> results = new HashSet<String>();
        if ("title".equals(attribute)) {
            results = service.searchBooksByTitle(sessionId, filter);
        } else if ("author".equals(attribute)) {
            results = service.searchBooksByAuthor(sessionId, filter);
        } else if ("category".equals(attribute)) {
            results = service.searchBooksByCategory(sessionId, filter);
        } else {
            throw new RuntimeException( "Invalid attribute, expecting one of { 'title', " +
                            "'author', 'category' } got '" + attribute + "'");
        }

        Set<Book> books = new HashSet<Book>();
        for (String isbn : results) {
            Book book = service.getBook(sessionId, isbn);
            books.add(book);
        }
        return books;
    }

    private BookshelfService lookupService() {
        ServiceReference serviceReference = context.getServiceReference(BookshelfService.class.getName());
        BookshelfService service = (BookshelfService) context.getService(serviceReference);
        return service;
    }


    @Descriptor("Search books by rating")
    public Set<Book> search(
            @Descriptor("username") String username,
            @Descriptor("password") String password,
            @Descriptor("search on attribute: rating") String attribute,
            @Descriptor("lower rating limit (inclusive)") int lower,
            @Descriptor("upper rating limit (inclusive)") int upper)
            throws InvalidCredentialsException
    {
        if (!"rating".equals(attribute))
        {

            [ 141 ]
            throw new RuntimeException(
                    "Invalid attribute, expecting 'rating' got '"+
                            attribute+"'");
        }
        BookshelfService service = lookupService();
        String sessionId =
                service.login(username, password.toCharArray());
        Set<String> results =
                service.searchBooksByRating(sessionId, lower, upper);
        return getBooks(sessionId, service, results);
        }

}