package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.FonctionDto;
import com.itma.gestionProjet.entities.Categorie;
import com.itma.gestionProjet.entities.Fonction;
import com.itma.gestionProjet.repositories.CategorieRepository;
import com.itma.gestionProjet.repositories.FonctionRepository;
import com.itma.gestionProjet.requests.CategorieRequest;
import com.itma.gestionProjet.services.FonctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FonctionServiceImpl implements FonctionService {

    @Autowired
    private FonctionRepository fonctionRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @Override
    public Page<FonctionDto> getAllFonctions(Pageable pageable) {
        return fonctionRepository.findAll(pageable).map(this::convertToDto);
    }

    @Override
    public FonctionDto getFonctionById(Long id) {
        Fonction fonction = fonctionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fonction not found"));
        return convertToDto(fonction);
    }

    @Override
    public FonctionDto createFonction(CategorieRequest fonctionDto) {
        if (fonctionRepository.existsByLibelle(fonctionDto.getLibelle())) {
            throw new RuntimeException("Fonction already exists with this libelle");
        }

        Categorie categorie = categorieRepository.findById(fonctionDto.getCategorieId())
                .orElseThrow(() -> new RuntimeException("Categorie not found"));

        Fonction fonction = new Fonction();
        fonction.setLibelle(fonctionDto.getLibelle());
        fonction.setCategorieRattache(categorie);

        return convertToDto(fonctionRepository.save(fonction));
    }

    @Override
    public FonctionDto updateFonction(Long id, CategorieRequest fonctionDto) {
        Fonction fonction = fonctionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fonction not found"));

        if (!fonction.getLibelle().equals(fonctionDto.getLibelle()) &&
                fonctionRepository.existsByLibelle(fonctionDto.getLibelle())) {
            throw new RuntimeException("Fonction with this libelle already exists");
        }

        Categorie categorie = categorieRepository.findById(fonctionDto.getCategorieId())
                .orElseThrow(() -> new RuntimeException("Categorie not found"));

        fonction.setLibelle(fonctionDto.getLibelle());
        fonction.setCategorieRattache(categorie);

        return convertToDto(fonctionRepository.save(fonction));
    }

    @Override
    public void deleteFonction(Long id) {
        fonctionRepository.deleteById(id);
    }

    private FonctionDto convertToDto(Fonction fonction) {
        FonctionDto dto = new FonctionDto();
        dto.setId(fonction.getId());
        dto.setLibelle(fonction.getLibelle());
        dto.setCategorie(fonction.getCategorieRattache());
        return dto;
    }
}
