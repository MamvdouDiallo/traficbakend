package com.itma.gestionProjet.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PartieInteresse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;
    private String statut;
    private String courrielPrincipal;
    private String adresse;
    private  String normes;
    private String localisation;
    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private CategoriePartieInteresse categoriePartieInteresse;
    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "partieInteresse")
    private List<User> contacts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

}
