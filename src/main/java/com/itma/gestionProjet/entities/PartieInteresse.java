package com.itma.gestionProjet.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
