package com.itma.gestionProjet.controllers;


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
@RequestMapping("/situation-matrimoniale")
public class SituationMatrimonialeController {

    @Autowired
    private SituationMatrimonialeService situationMatrimonialeService;

    @GetMapping("")
    public ResponseEntity<AApiResponse<SituationMatrimoniale>> getSituationsMatrimoniales(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int max) {

        Page<SituationMatrimoniale> situationsPage = situationMatrimonialeService.getSituationsMatrimoniales(offset, max);

        AApiResponse<SituationMatrimoniale> response = new AApiResponse<>();
        response.setResponseCode(200);
        response.setData(situationsPage.getContent());
        response.setOffset(offset);
        response.setMax(max);
        response.setLength(situationsPage.getTotalElements());

        return ResponseEntity.ok().body(response);
    }
}
