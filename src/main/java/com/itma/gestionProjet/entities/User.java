package com.itma.gestionProjet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private Boolean enabled;
    private String password;
    private String locality;
    private String contact;
    private String imageUrl;
    private String sexe;

    // Relation ManyToOne avec Fonction
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fonction_id")
    private Fonction fonction;

    // Relation ManyToOne avec Categorie
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    // Relation ManyToMany avec Role
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private List<Role> roles;

    // Relation ManyToMany avec Project
    @JsonIgnore
    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Project> projects;

    // Relation ManyToOne avec PartieInteresse
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partie_interesse_id")
    private PartieInteresse partieInteresse;
}

