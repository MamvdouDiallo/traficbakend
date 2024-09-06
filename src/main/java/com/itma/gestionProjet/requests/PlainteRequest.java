package com.itma.gestionProjet.requests;

import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class PlainteRequest {

    private String numeroDossier;
    private String lieuEnregistrement;
    private Date dateEnregistrement;
    private Boolean isRecensed;
    private String lieuNaissance;
    private String nom;
    private String prenom;
    private String numeroIdentification;
    private String placeOfBirth;
    private String recommandation;
    private String situationMatrimoniale;
    private String typeIdentification;
    private String vulnerabilite;
    private Long projectId;
    private String codePap;
    private List<String> documentUrls;
    private  String etat;

    // Getters and Setters
}