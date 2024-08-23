package com.itma.gestionProjet.dtos;

import lombok.Data;

@Data
public class EmployePapDTO {
    private Long id;

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

    private String parentKey;

    private String key;

    private String childKey;

    private String infoComplementaire;
}
