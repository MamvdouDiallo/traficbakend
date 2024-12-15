package com.itma.gestionProjet.requests;

import lombok.Data;

@Data

public class CategorieRequest {
    private Long id;
    private String libelle;
    private Long categorieId;
}
