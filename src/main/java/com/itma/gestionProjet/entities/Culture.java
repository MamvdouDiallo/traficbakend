package com.itma.gestionProjet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Entity
@Data
public class Culture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(nullable = false)
    private String codePap;
    @NotNull
    @Column(nullable = false)
    private String typeCulture;
    @NotNull
@Column(nullable = false)
    private String description;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "personneAffecte_id")
    private PersonneAffecte personneAffecte;
}
