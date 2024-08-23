package com.itma.gestionProjet.requests;


import lombok.Data;

@Data
public class DescriptionEquipementRequest {
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
    private String parentKey;
    private String key;
    private String childKey;

}
