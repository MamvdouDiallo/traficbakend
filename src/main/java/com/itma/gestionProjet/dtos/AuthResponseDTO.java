package com.itma.gestionProjet.dtos;


import com.itma.gestionProjet.entities.User;
import lombok.Data;

import java.util.Optional;

@Data
public class AuthResponseDTO {
    private String token;
  //  private String tokenType = "Bearer ";
    Optional<UserDTO> user;

    public AuthResponseDTO(String token, Optional<UserDTO> user) {
        this.token = token;
        this.user=user;
    }
}