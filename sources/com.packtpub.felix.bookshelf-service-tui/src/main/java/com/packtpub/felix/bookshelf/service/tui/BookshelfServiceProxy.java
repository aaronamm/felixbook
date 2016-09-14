package com.packtpub.felix.bookshelf.service.tui;

import com.packtpub.felix.bookshelf.service.api.BookshelfService;
import com.packtpub.felix.bookshelf.service.api.InvalidCredentialsException;
import com.packtpub.felix.bookshelf.service.impl.BookInventoryNotRegisteredRuntimeException;
import com.packtpub.felix.bookshelf.service.impl.SessionNotValidRuntimeException;
import org.apache.felix.service.command.Descriptor;
import org.osgi.framework.BundleContext;

import java.awt.print.Book;
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
            @Descriptor( "match like (use % at the beginning or end of <like>" +
                            " for wild-card)") String filter)
            throws InvalidCredentialsException {
        BookshelfService service = lookupService();
        String sessionId = service.login(
                username, password.toCharArray());
        Set<String> results;
        if ("title".equals(attribute)) {
            try {
                results = service.searchBooksByTitle(sessionId, filter);
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if ("author".equals(attribute)) {
            results = service.searchBooksByAuthor(sessionId, filter);

        }

    private BookshelfService lookupService() {
    }

}