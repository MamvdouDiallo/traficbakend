package com.itma.gestionProjet.controllers;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.entities.CategoriePartieInteresse;
import com.itma.gestionProjet.services.CategoriePartieInteresseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoriesPip")
public class CategoriePartieInteresseController {

    @Autowired
    private CategoriePartieInteresseService service;

    @GetMapping
    public ResponseEntity<AApiResponse<CategoriePartieInteresse>> getCategories(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int max) {

        PageRequest pageRequest = PageRequest.of(offset, max);
        Page<CategoriePartieInteresse> categoriesPage = service.getCategories(pageRequest);

        AApiResponse<CategoriePartieInteresse> response = new AApiResponse<>();
        response.setResponseCode(200);
        response.setData(categoriesPage.getContent());
        response.setOffset(offset);
        response.setMax(max);
        response.setLength(categoriesPage.getTotalElements());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public CategoriePartieInteresse getCategorieById(@PathVariable Long id) {
        return service.getCategorieById(id);
    }

    @PostMapping
    public CategoriePartieInteresse createCategorie(@RequestBody CategoriePartieInteresse categorie) {
        return service.createCategorie(categorie);
    }

    @PutMapping("/{id}")
    public CategoriePartieInteresse updateCategorie(@PathVariable Long id, @RequestBody CategoriePartieInteresse categorie) {
        return service.updateCategorie(id, categorie);
    }

    @DeleteMapping("/{id}")
    public void deleteCategorie(@PathVariable Long id) {
        service.deleteCategorie(id);
    }
}