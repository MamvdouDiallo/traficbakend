package com.itma.gestionProjet.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    @Value("${app.version}")
    private String appVersion;

    @GetMapping("/default")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Bienvenue sur l'API du projet Invodis - Version: " + appVersion);
    }
}

