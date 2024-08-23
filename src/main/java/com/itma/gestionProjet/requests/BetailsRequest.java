package com.itma.gestionProjet.requests;

import lombok.Data;

@Data
public class BetailsRequest {
    private String codePap;
    private String typeBetails;
    private String description;
}
