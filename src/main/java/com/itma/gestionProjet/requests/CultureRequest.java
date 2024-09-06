package com.itma.gestionProjet.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CultureRequest {
    @NotNull(message = "Le codePap est obligatoire")
    private String codePap;
    @NotNull(message = "Le type de culture est obligatoire")
    private String typeCulture;
    @NotNull(message = "La description est obligatoire")
    private String description;
}
