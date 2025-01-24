package com.itma.gestionProjet.exceptions;



public class FonctionNotFoundException extends RuntimeException {
    public FonctionNotFoundException(String message) {
        super(message);
    }
}