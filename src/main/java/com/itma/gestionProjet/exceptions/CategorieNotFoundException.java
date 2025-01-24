package com.itma.gestionProjet.exceptions;



public class CategorieNotFoundException extends RuntimeException {
    public CategorieNotFoundException(String message) {
        super(message);
    }
}