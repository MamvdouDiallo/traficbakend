package com.itma.gestionProjet.entities;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class BaremeEquipement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categorie;
    private Double prixUnite;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
