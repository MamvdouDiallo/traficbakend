package com.itma.gestionProjet.dtos;


import lombok.Data;

@Data
public class BaremeEquipementDTO {
    private Long id;
    private String categorie;
    private Double prixUnite;
    private Long projectId;
}
