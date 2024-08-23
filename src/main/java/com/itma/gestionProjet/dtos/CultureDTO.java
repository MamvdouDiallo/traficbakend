package com.itma.gestionProjet.dtos;

import lombok.Data;

@Data
public class CultureDTO {
    private Long id;
    private String codePap;
    private String typeCulture;
    private String description;
}