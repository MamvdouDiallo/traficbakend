package com.itma.gestionProjet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @NotBlank(message = "Le libelle est obligatoire et ne peut pas être vide")
    @Column(nullable = false)
    private String libelle;
    @NotNull
    @NotBlank(message = "La description est obligatoire et ne peut pas être vide")
    @Column(nullable = false)
    private String description;
    private String status;
    private String imageUrl;
    private Date datedebut;
    private Date datefin;

    @OneToMany(mappedBy = "project")
    private List<NormeProjet> normes;
   // @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "user_project",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private List<User> users;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<PersonneAffecte> personnesAffectees ;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<PartieInteresse> partiesInteressees ;


}
