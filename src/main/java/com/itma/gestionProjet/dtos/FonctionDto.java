package com.itma.gestionProjet.dtos;


import com.itma.gestionProjet.entities.Categorie;
import lombok.Data;

@Data
public class FonctionDto {
    private Long id;
    private String libelle;
    private Categorie categorie;
}
