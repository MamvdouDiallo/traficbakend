package com.itma.gestionProjet.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Description {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String geo;
    private String codePap;
    private String codeEquipement;
    private Integer nombreEquipement;
    private Integer count;
    private String photo;
    private String typeEquipement;
    private String etatEquipement;
    private String proprietaire;
    private String infoComplementaire;
  //  private String parentKey;
  //  private String key;
  //  private String childKey;

    @ManyToOne
    @JoinColumn(name = "personneAffecte_id")
    private PersonneAffecte personneAffecte;
}
