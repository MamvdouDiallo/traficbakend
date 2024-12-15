package com.itma.gestionProjet.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlainteUserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String complaintType;
    private String complaintDescription;
    private String etat;
    private LocalDateTime createdAt;
}
