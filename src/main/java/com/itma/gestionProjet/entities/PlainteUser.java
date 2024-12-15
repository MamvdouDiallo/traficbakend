package com.itma.gestionProjet.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "plainte_user")
@Data
public class PlainteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String complaintType;

    @Column(name = "complaint_description", columnDefinition = "TEXT")
    private String complaintDescription;
    private String etat;
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
