package com.itma.gestionProjet.controllers;


import com.itma.gestionProjet.entities.NormeProjet;
import com.itma.gestionProjet.requests.NormeProjetRequest;
import com.itma.gestionProjet.services.imp.NormeProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/normes")
public class NormeProjetController {


    @Autowired
    NormeProjetService normeProjetService ;

    @PostMapping("/saveNorme/{projectId}")
    public NormeProjet saveNorme(@RequestBody NormeProjet normeProjet, @PathVariable Long projectId) {
        return normeProjetService.saveNormeProjet1(normeProjet, projectId);
    }

    @PostMapping(value = "/update/{projectId}")
    public List<NormeProjet> update(@RequestBody List<NormeProjet> normeProjects, @PathVariable Long projectId) {
        return normeProjetService.saveNormeProjet(normeProjects, projectId);
    }

    
}
