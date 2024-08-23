package com.itma.gestionProjet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class GeoPoly {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String geometrie;
    private String uid;
    private String axe;
    private String commune;
    private String codeRevise;
    private String codePap;
    private String prenomNom;
    private String localisation;
    private String zoneRegroupement;
    private String pkZoneRegroupement;
    private String typeImpact;
    private String infoComplementaire;
@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "personneAffecte_id")
    private PersonneAffecte personneAffecte;
}
