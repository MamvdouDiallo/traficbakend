package com.itma.gestionProjet.dtos;


import lombok.Data;

import java.util.Map;

@Data
public class ModificationEntenteDTO {
    private Long ententeId;
    private Map<String, Object> modifications;
    private String raisonModification;
    private String email;
}