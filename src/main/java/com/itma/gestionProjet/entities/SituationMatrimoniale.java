package com.itma.gestionProjet.entities;

import jakarta.persistence.*;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "situation_matrimoniale")
public class SituationMatrimoniale {

    @Id
    private String id;

    @Version
    private Long version;

    @Column(name = "libelle", length = 50)
    private String libelle;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Column(name = "user_create")
    private String userCreate;

    @Column(name = "user_update")
    private String userUpdate;
}
