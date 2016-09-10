package com.packtpub.felix.bookshelf.service.api;

import com.packtpub.felix.bookshelf.service.impl.SessionNotValidRuntimeException;

public interface Authentication {
    String login(String username, char[] password)
throws InvalidCredentialsException;
void logout(String sessionId) throws SessionNotValidRuntimeException;
boolean sessionIsValid(String sessionId);
}
