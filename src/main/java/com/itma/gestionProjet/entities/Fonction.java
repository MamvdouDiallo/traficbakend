package com.itma.gestionProjet.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Entity
public class Fonction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    @NotNull
    private String libelle;

    @ManyToOne
    @JoinColumn(name = "categorie_id", nullable = false)
    @NotNull
    private Categorie categorieRattache;
    /*
    @OneToOne(mappedBy = "fonction")
    private User user;

     */

}
