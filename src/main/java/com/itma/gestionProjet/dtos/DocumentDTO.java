package com.itma.gestionProjet.dtos;

import com.itma.gestionProjet.entities.CategorieDocument;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class DocumentDTO {
    private Long id;
    private String libelle;
    private String urlDocument;
    private CategorieDocument categorie;


}
