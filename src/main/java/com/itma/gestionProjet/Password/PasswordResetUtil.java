package com.itma.gestionProjet.Password;


import lombok.Data;

@Data
public class PasswordResetUtil {
    private String token;
    private String newPassword;
}
