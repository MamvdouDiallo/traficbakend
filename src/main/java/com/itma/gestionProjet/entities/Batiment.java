package com.itma.gestionProjet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Data
@Entity
public class Batiment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String geo;
    private String codePap;
    private String codeRevise;
    private String codeBatiment;
    private String contour;
    private String typeBatiment;
    private Integer nombrePiece;
    private Integer nombrePieceUtilise;
    private String etatBatiment;
    private String natureSol;
    private String structureMur;
    private String typeToiture;
    private String autreTypeToiture;
    private String porteFentre;
    private String propriete;
    private String infoComplementaire;
    private String decriptionBatiment;
    private String parentcle;
    private String cle;
    private String childcle;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personne_affecte_id")
    private PersonneAffecte personneAffecte;
}
