package com.packtpub.felix.bookshelf.service.tui;

import com.packtpub.felix.bookshelf.inventory.api.Book;
import com.packtpub.felix.bookshelf.inventory.api.BookNotFoundException;
import com.packtpub.felix.bookshelf.service.api.BookInventoryNotRegisteredRuntimeException;
import com.packtpub.felix.bookshelf.service.api.BookshelfService;
import com.packtpub.felix.bookshelf.service.api.SessionNotValidRuntimeException;
import org.apache.felix.ipojo.annotations.*;
import org.apache.felix.service.command.Descriptor;
import org.osgi.framework.ServiceReference;
import java.util.HashSet;
import java.util.Set;

@Component(name = "BookshelfServiceProxyImp")
@Provides
@Instantiate(name = "BookshelfServiceProxyImpInstance")
public class BookshelfServiceProxyImp implements BookshelfServiceProxy {

    @Requires
    private  BookshelfService service;

    @ServiceProperty(name = "osgi.command.scope",value = SCOPE)
    private String gogoScope;

    @ServiceProperty(name = "osgi.command.function", value = FUNCTIONS)
    private String[] gogoFunctions;

    @Descriptor("Search books by author, title, or category")
    public Set<Book> search(
            @Descriptor("username") String username,
            @Descriptor("password") String password,
            @Descriptor("search on attribute: author, title, or category") String attribute,
            @Descriptor("match like (use % at the beginning or end of <like>" +
                    " for wild-card)") String filter) {
        try {
            String sessionId = service.login(username, password.toCharArray());

            Set<String> results = new HashSet<String>();
            if ("title".equals(attribute)) {
                results = service.searchBooksByTitle(sessionId, filter);
            } else if ("author".equals(attribute)) {
                results = service.searchBooksByAuthor(sessionId, filter);
            } else if ("category".equals(attribute)) {
                results = service.searchBooksByCategory(sessionId, filter);
            } else {
                throw new RuntimeException("Invalid attribute, expecting one of { 'title', " + "'author', 'category' } got '" + attribute + "'");
            }

            return getBooks(sessionId, service, results);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new HashSet<Book>();
        }
    }



    @Descriptor("Search books by rating")
    public Set<Book> search(@Descriptor("username") String username, @Descriptor("password") String password,
                            @Descriptor("search on attribute: rating") String attribute,
                            @Descriptor("lower rating limit (inclusive)") int lower,
                            @Descriptor("upper rating limit (inclusive)") int upper) {
        try {
            if (!"rating".equals(attribute)) {
                throw new RuntimeException("Invalid attribute, expecting 'rating' got '" + attribute + "'");
            }
            String sessionId = service.login(username, password.toCharArray());
            Set<String> results = service.searchBooksByRating(sessionId, lower, upper);
            return getBooks(sessionId, service, results);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new HashSet<Book>();
        }
    }

    private Set<Book> getBooks(String sessionId, BookshelfService service, Set<String> results) throws SessionNotValidRuntimeException, BookNotFoundException, BookInventoryNotRegisteredRuntimeException {

        Set<Book> books = new HashSet<Book>();
        for (String isbn : results) {
            Book book = service.getBook(sessionId, isbn);
            books.add(book);
        }
        return books;
    }

    public String add(@Descriptor("username") String username,
                      @Descriptor("password") String password,
                      @Descriptor("ISBN") String isbn,
                      @Descriptor("Title") String title,
                      @Descriptor("Author") String author,
                      @Descriptor("Category") String category,
                      @Descriptor("Rating (0..10)") int rating) {
        try {
            String sessionId = service.login(
                    username, password.toCharArray());
            service.addBook( sessionId, isbn, title, author, category, rating);
            return isbn;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @PostRegistration
    public  void registered(ServiceReference serviceReference){
        System.out.println( this.getClass().getSimpleName() + " registered");
    }

    @PostUnregistration
    public  void unregistered(ServiceReference serviceReference){
        System.out.println( this.getClass().getSimpleName() + " unregistered");
    }
}