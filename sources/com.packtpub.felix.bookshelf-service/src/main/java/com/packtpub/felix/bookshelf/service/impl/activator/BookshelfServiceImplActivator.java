package com.packtpub.felix.bookshelf.service.impl.activator;

import com.packtpub.felix.bookshelf.inventory.api.*;
import com.packtpub.felix.bookshelf.service.api.*;
import com.packtpub.felix.bookshelf.service.impl.*;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import java.util.Set;

public class BookshelfServiceImplActivator
        implements BundleActivator {
    ServiceRegistration reg = null;

    public void start(BundleContext context) throws Exception {
        this.reg = context.registerService(BookshelfService.class.getName(), new BookshelfServiceImpl(context), null);
        try {
            testService(context);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public void stop(BundleContext context) throws Exception {
        if (this.reg != null) {
            context.ungetService(reg.getReference());
        }
    }


    private void testService(BundleContext context) {
        // retrieve service
        String name = BookshelfService.class.getName();
        ServiceReference ref = context.getServiceReference(name);
        if (ref == null) {
            throw new RuntimeException(
                    "Service not registered: " + name);
        }
        BookshelfService service =
                (BookshelfService) context.getService(ref);

        // authenticate and get session
        String sessionId;
        try {
            System.out.println("\nSigning in. . .");
            sessionId =
                    service.login("admin", "admin".toCharArray());
        } catch (InvalidCredentialsException e) {
            e.printStackTrace();
            return;
        }
// add a few books
        try {
            System.out.println("\nAdding books. . .");
            service.addBook(sessionId, "123-4567890100", "Book 1 Title", "John Doe", "Group 1", 0);
            service.addBook(sessionId, "123-4567890101", "Book 2 Title", "Will Smith", "Group 1", 0);
            service.addBook(sessionId, "123-4567890200", "Book 3 Title", "John Doe", "Group 2", 0);
            service.addBook(sessionId, "123-4567890201", "Book 4 Title", "Jane Doe", "Group 2", 0);
        } catch (BookAlreadyExistsException e) {
            e.printStackTrace();
            return;
        } catch (InvalidBookException e) {
            e.printStackTrace();
            return;
        } catch (SessionNotValidRuntimeException e) {
            e.printStackTrace();
        } catch (BookInventoryNotRegisteredRuntimeException e) {
            e.printStackTrace();
        }

        // and test search
        String authorLike = "%Doe";
        System.out.println( "Searching for books with author like: " + authorLike);
        Set<String> results = null;
        try {
            results = service.searchBooksByAuthor(sessionId, authorLike);
        } catch (SessionNotValidRuntimeException e) {
            e.printStackTrace();
        } catch (BookInventoryNotRegisteredRuntimeException e) {
            e.printStackTrace();
        }

        for (String isbn : results) {
            try {
                System.out.println(" - " + service.getBook(sessionId, isbn));
            } catch (BookNotFoundException e) {
                System.err.println(e.getMessage());
            } catch (SessionNotValidRuntimeException e) {
                e.printStackTrace();
            } catch (BookInventoryNotRegisteredRuntimeException e) {
                e.printStackTrace();
            }
        }
    }

}
