package com.itma.gestionProjet.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class EmployePapRequest {

    @NotNull
    private String codePap;

    private String codeEmploye;

    private String numeroIdentifiant;

    private String prenomNom;

    private String contactTelephonique;

    private String categorieActivite;

    private String sexe;

    private int age;

    private String nationalite;

    private String qualiteEmploye;

    private double remuneration;

    private double prime;

    private String handicap;

    private String parentcle;

    private String cle;

    private String childcle;

    private String infoComplementaire;

}

