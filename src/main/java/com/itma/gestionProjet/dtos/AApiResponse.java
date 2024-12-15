package com.itma.gestionProjet.dtos;

import lombok.Data;

import java.util.List;


public class AApiResponse<T> {

    private int responseCode;
    private List<T> data;
    private int offset;
    private int max;
    private String message;
    private long length;

    // Constructeur par défaut requis par Spring
    public AApiResponse() {
    }

    // Constructeur avec tous les arguments
    public AApiResponse(int responseCode, List<T> data, int offset, int max, String message, long length) {
        this.responseCode = responseCode;
        this.data = data;
        this.offset = offset;
        this.max = max;
        this.message = message;
        this.length = length;
    }

    // Getters et Setters
    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }
}

