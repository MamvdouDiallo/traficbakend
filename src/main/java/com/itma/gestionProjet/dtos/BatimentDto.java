package com.itma.gestionProjet.dtos;


import lombok.Data;

@Data
public class BatimentDto {
    private Long id;
    private String geo;
    private String codePap;
    private String codeRevise;
    private String codeBatiment;
    private String contour;
    private String typeBatiment;
    private int nombrePiece;
    private int nombrePieceUtilise;
    private String etatBatiment;
    private String natureSol;
    private String structureMur;
    private String typeToiture;
    private String autreTypeToiture;
    private String porteFentre;
    private String propriete;
    private String infoComplementaire;
    private String decriptionBatiment;
    private String parentKey;
    private String key;
    private String childKey;

    // Getters and Setters
}
