package com.itma.gestionProjet.dtos;


import lombok.Data;

@Data
public class FileUploadResponse {
    private int code;
    private String message;
    private String fileName;

    public FileUploadResponse(int code, String message, String fileName) {
        this.code = code;
        this.message = message;
        this.fileName = fileName;
    }
}
