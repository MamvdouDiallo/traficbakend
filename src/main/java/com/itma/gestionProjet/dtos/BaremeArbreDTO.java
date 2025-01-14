package com.itma.gestionProjet.dtos;

import lombok.Data;

import java.io.Serializable;


@Data
public class BaremeArbreDTO implements Serializable {
    private Long id;
    private String espece;
    private Double prixArbreProductif;
    private Double prixArbreNonProductif;
    private Double prixParKg;
    private Integer ageDebutProduction;
    private Long projectId;
}

