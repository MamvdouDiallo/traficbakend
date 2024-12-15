package com.itma.gestionProjet.dtos;


import lombok.Data;

import java.util.List;
@Data
public class AResponse<T> {
    private int responseCode;
    private String message;
    private T data;
}
