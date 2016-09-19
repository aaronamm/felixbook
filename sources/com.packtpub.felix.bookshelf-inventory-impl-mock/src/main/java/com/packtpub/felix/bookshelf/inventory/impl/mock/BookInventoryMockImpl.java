package com.packtpub.felix.bookshelf.inventory.impl.mock;

import com.packtpub.felix.bookshelf.inventory.api.*;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;

import java.util.*;

@Component
@Provides
@Instantiate(name = "BookInventoryMockImpl")
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

    public MutableBook loadBookForEdit(String isbn) throws BookNotFoundException {
        MutableBook book = this.booksByISBN.get(isbn);
        if (book == null) {
            throw new BookNotFoundException(isbn);
        }
        return book;
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

    public Book loadBook(String isbn) throws BookNotFoundException {
        return loadBookForEdit(isbn);
    }

    public void removeBook(String s) throws BookNotFoundException {

    }

    public Set<String> searchBooks(Map<SearchCriteria, String> criteria) {
        LinkedList<Book> books = new LinkedList<Book>();
        books.addAll(this.booksByISBN.values());
        for (Map.Entry<SearchCriteria, String> criterion : criteria.entrySet()) {
            Iterator<Book> it = books.iterator();
            while (it.hasNext()) {
                Book book = it.next();
                switch (criterion.getKey()) {
                    case AUTHOR_LIKE:
                        if (!checkStringMatch(book.getAuthor(), criterion.getValue())) {
                            it.remove();
                            continue;
                        }
                        break;
                    case ISBN_LIKE:
                        if (!checkStringMatch(book.getIsbn(), criterion.getValue())) {
                            it.remove();
                            continue;
                        }
                        break;
                    case CATEGORY_LIKE:
                        if (!checkStringMatch(book.getCategory(), criterion.getValue())) {
                            it.remove();
                            continue;
                        }
                        break;
                    case TITLE_LIKE:
                        if (!checkStringMatch(book.getTitle(), criterion.getValue())) {
                            it.remove();
                            continue;
                        }
                        break;
                    case RATE_GT:
                        if (!checkIntegerGreater(book.getRating(), criterion.getValue())) {
                            it.remove();
                            continue;
                        }
                        break;
                    case RATE_LT:
                        if (!checkIntegerSmaller(book.getRating(), criterion.getValue())) {
                            it.remove();
                            continue;
                        }
                        break;
                }


            }
        }

        //copy match with criteria book
        HashSet<String> isbns = new HashSet<String>();
        for(Book book : books){
            isbns.add(book.getIsbn());
        }
        return isbns;
    }

    private boolean checkStringMatch(String attr, String critVal) {
        if (attr == null) {
            return false;
        }
        attr = attr.toLowerCase();
        critVal = critVal.toLowerCase();
        boolean startsWith = critVal.startsWith("%");
        boolean endsWith = critVal.endsWith("%");

        if (startsWith && endsWith) {
            if (critVal.length() == 1) {//greedy criVal = "%"
                return true;
            } else {
                //"%t%" 1, length-1
                return attr.contains(critVal.substring(1, critVal.length() - 1));
            }
        } else if (startsWith) {
            //%test
            return attr.endsWith(critVal.substring(1));
        } else if (endsWith) {
            //test%
            return attr.startsWith(attr.substring(0, attr.length() - 1));
        } else {
            return attr.equals(critVal);
        }

    }

    private boolean checkIntegerGreater(int attr, String critVal) {
        int critValInt;
        try {
            critValInt = Integer.parseInt(critVal);
        } catch (NumberFormatException ex) {
            return false;
        }
        if (attr >= critValInt) {
            return true;
        }
        return false;

    }

    private boolean checkIntegerSmaller(int attr, String critVal) {

        int critValInt;
        try {
            critValInt = Integer.parseInt(critVal);
        } catch (NumberFormatException ex) {
            return false;
        }

        if (attr <= critValInt) {
            return true;
        }
        return false;
    }
}
