package com.itma.gestionProjet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DescriptionEquipement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String geo;
    private String codePap;
    private String codeEquipement;
    private int nombreEquipement;
    private int count;
    private String photo;
    private String typeEquipement;
    private String etatEquipement;
    private String proprietaire;
    private String infoComplementaire;
    private String parentcle;
    private String cle;
    private String childcle;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "personneAffecte_id")
    private PersonneAffecte personneAffecte;
}
