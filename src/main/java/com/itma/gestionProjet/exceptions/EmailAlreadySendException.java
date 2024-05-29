package com.itma.gestionProjet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmailAlreadySendException extends RuntimeException {
    private String message;

    public EmailAlreadySendException(String message)
    {
        super(message);
    }

}