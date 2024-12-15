package com.itma.gestionProjet.dtos;

import com.itma.gestionProjet.requests.PlainteRequest;
import lombok.Data;


@Data
public class PlainteInvalidDto {

    private PlainteRequest plainteRequest;
    private String errorMessage;
}
