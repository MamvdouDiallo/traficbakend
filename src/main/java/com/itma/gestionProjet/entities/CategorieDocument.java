package com.itma.gestionProjet.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Data
public class CategorieDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    /*
    @OneToMany(mappedBy = "categorieDocument")
    private List<Document> documents;

     */
}
