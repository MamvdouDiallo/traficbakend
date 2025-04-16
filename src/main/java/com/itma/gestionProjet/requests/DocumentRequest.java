package com.itma.gestionProjet.requests;


import lombok.Data;

@Data
public class DocumentRequest {
    private String libelle;
    private String urlDocument;
    private Long projectId;
    private Long categorieDocumentId;
}
