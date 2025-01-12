package com.itma.gestionProjet.requests;


import lombok.Data;

import java.io.Serializable;

@Data
public class BaremeArbreRequest implements Serializable {
    private String espece;
    private Double prixArbreProductif;
    private Double prixArbreNonProductif;
    private Double prixParKg;
    private Integer ageDebutProduction;
    private Long projectId;
}
