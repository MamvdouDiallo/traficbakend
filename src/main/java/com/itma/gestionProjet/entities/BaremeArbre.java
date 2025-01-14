package com.itma.gestionProjet.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class BaremeArbre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String espece;

    private Double prixArbreProductif;

    private Double prixArbreNonProductif;

    private Double prixParKg;

    private Integer ageDebutProduction;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

}
