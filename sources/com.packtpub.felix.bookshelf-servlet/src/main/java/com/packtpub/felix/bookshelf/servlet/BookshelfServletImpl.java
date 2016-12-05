package com.packtpub.felix.bookshelf.servlet;

import com.packtpub.felix.bookshelf.log.api.BookShelfLogHelper;
import com.packtpub.felix.bookshelf.service.api.BookshelfService;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BookshelfServletImpl extends HttpServlet {

    private String alias;
    private BookshelfService service;
    private BookShelfLogHelper logger;
    private String sessionId;

    public void init(ServletConfig config) {

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

    }

}
