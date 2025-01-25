package com.itma.gestionProjet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;


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

    @JsonIgnore
    // Relation OneToMany avec User
    @OneToMany(mappedBy = "fonction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;
    /*
    @OneToOne(mappedBy = "fonction")
    private User user;

     */

}
