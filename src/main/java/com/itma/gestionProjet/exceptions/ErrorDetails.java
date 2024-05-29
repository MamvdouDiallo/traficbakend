package com.itma.gestionProjet.exceptions;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String path;
    private String errorCode;

    // Constructeur avec initialisation des champs
    public ErrorDetails(LocalDateTime timestamp, String message, String path, HttpStatus errorCode) {
        this.timestamp = timestamp;
        this.message = message;
        this.path = path;
        this.errorCode = String.valueOf(errorCode);
    }
}
