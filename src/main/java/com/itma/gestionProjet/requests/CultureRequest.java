package com.itma.gestionProjet.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CultureRequest {
    @NotBlank(message = "Le codePap est obligatoire")
    private String codePap;
    @NotBlank(message = "Le type de culture ne peut pas etre null")
    private String typeCulture;
    @NotBlank(message = "La description est obligatoire")
    private String description;
}
