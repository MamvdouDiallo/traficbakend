package com.itma.gestionProjet.dtos;


import lombok.Data;

@Data
public class ResponseData<T>  {
    private Integer status;
    private String message;
    private T data;

    public ResponseData(Integer status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = (T) data;
    }


}