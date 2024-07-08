package com.itma.gestionProjet.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ImageNotFoundException extends  RuntimeException{
    private String message;
    public ImageNotFoundException(String message) {
        super(message);
    }


}
