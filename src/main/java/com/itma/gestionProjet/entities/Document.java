package com.itma.gestionProjet.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;

    private String urlDocument;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "projet_id", nullable = false)
    private Project projet;

    @ManyToOne
    @JoinColumn(name = "categorie_document_id", nullable = false)
    private CategorieDocument categorieDocument;
}
