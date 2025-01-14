package com.itma.gestionProjet.requests;


import lombok.Data;

@Data
public class BaremeEquipementRequest {
    private String categorie;
    private Double prixUnite;
    private Long projectId;
}
