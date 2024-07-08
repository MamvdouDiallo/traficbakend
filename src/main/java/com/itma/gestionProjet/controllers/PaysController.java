package com.itma.gestionProjet.controllers;


import com.itma.gestionProjet.entities.Pays;
import com.itma.gestionProjet.services.imp.PaysService;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.entities.SituationMatrimoniale;
import com.itma.gestionProjet.services.imp.SituationMatrimonialeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/pays")
public class PaysController {

    @Autowired
    private PaysService paysService;
    @GetMapping("")
    public ResponseEntity<AApiResponse<Pays>> getPays(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int max) {

        Page<Pays> paysPage = paysService.getPays(offset, max);

        AApiResponse<Pays> response = new AApiResponse<>();
        response.setResponseCode(200);
        response.setData(paysPage.getContent());
        response.setOffset(offset);
        response.setMax(max);
        response.setLength(paysPage.getTotalElements());

        return ResponseEntity.ok().body(response);
    }
}
