package com.packtpub.felix.bookshelf.log.api;

public interface BookShelfLogHelper {
    void debug(String pattern, Object... args);
    void debug(String pattern, Throwable throwable, Object... args);
}
