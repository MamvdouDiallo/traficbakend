package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.CategorieDto;
import com.itma.gestionProjet.entities.Categorie;
import com.itma.gestionProjet.repositories.CategorieRepository;
import com.itma.gestionProjet.services.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Service
public class CategorieServiceImpl implements CategorieService {

    @Autowired
    private CategorieRepository categorieRepository;

    @Override
    public Page<CategorieDto> getAllCategories(Pageable pageable) {
        return categorieRepository.findAll(pageable).map(this::convertToDto);
    }


    @Override
    public CategorieDto getCategorieById(Long id) {
        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categorie not found"));
        return convertToDto(categorie);
    }
    @Override
    public CategorieDto createCategorie(CategorieDto categorieDto) {
        if (categorieRepository.existsByLibelle(categorieDto.getLibelle())) {
            throw new RuntimeException("Categorie already exists with this libelle");
        }

        Categorie categorie = new Categorie();
        categorie.setLibelle(categorieDto.getLibelle());

        return convertToDto(categorieRepository.save(categorie));
    }

    @Override
    public CategorieDto updateCategorie(Long id, CategorieDto categorieDto) {
        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categorie not found"));

        if (!categorie.getLibelle().equals(categorieDto.getLibelle()) &&
                categorieRepository.existsByLibelle(categorieDto.getLibelle())) {
            throw new RuntimeException("Categorie with this libelle already exists");
        }

        categorie.setLibelle(categorieDto.getLibelle());
        return convertToDto(categorieRepository.save(categorie));
    }

    @Override
    public void deleteCategorie(Long id) {
        categorieRepository.deleteById(id);
    }

    private CategorieDto convertToDto(Categorie categorie) {
        CategorieDto dto = new CategorieDto();
        dto.setId(categorie.getId());
        dto.setLibelle(categorie.getLibelle());
        return dto;
    }
}
