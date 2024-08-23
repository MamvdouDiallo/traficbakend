package com.itma.gestionProjet.services.imp;


import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.CultureDTO;
import com.itma.gestionProjet.entities.Coproprietaire;
import com.itma.gestionProjet.entities.Culture;
import com.itma.gestionProjet.entities.PersonneAffecte;
import com.itma.gestionProjet.repositories.CultureRepository;
import com.itma.gestionProjet.repositories.PersonneAffecteRepository;
import com.itma.gestionProjet.requests.CultureRequest;
import com.itma.gestionProjet.services.CultureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CultureServiceImpl implements CultureService {

    @Autowired
    private CultureRepository cultureRepository;


    @Autowired
    private PersonneAffecteRepository personneAffecteRepository;

    @Override
    public List<CultureDTO> createCulture(List<CultureRequest> cultureRequests) {
        List<CultureDTO> cultureDTOs = new ArrayList<>();

        for (CultureRequest request : cultureRequests) {
            PersonneAffecte personneAffecte = (PersonneAffecte) personneAffecteRepository.findByCodePap(request.getCodePap())
                    .orElseThrow(() -> new RuntimeException("PersonneAffecte avec codePap " + request.getCodePap() + " non trouvée"));

            Culture culture = new Culture();
            culture.setCodePap(request.getCodePap());
            culture.setTypeCulture(request.getTypeCulture());
            culture.setDescription(request.getDescription());
            culture.setPersonneAffecte(personneAffecte);

            Culture savedCulture = cultureRepository.save(culture);

            CultureDTO cultureDTO = new CultureDTO();
            cultureDTO.setId(savedCulture.getId());
            cultureDTO.setCodePap(savedCulture.getCodePap());
            cultureDTO.setTypeCulture(savedCulture.getTypeCulture());
            cultureDTO.setDescription(savedCulture.getDescription());

            cultureDTOs.add(cultureDTO);
        }

        return cultureDTOs;
    }

    @Override
    public AApiResponse<Culture> getAllCultures(int offset, int max) {
        AApiResponse<Culture> response = new AApiResponse<>();
        try {
            List<Culture> cultures = cultureRepository.findAll(PageRequest.of(offset, max)).getContent();
            response.setResponseCode(200);
            response.setData(cultures);
            response.setOffset(offset);
            response.setMax(max);
            response.setMessage("Liste des cultures récupérée avec succès");
            response.setLength(cultureRepository.count());
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Erreur lors de la récupération des copropriétaires : " + e.getMessage());
            response.setData(new ArrayList<>()); // Retourne une liste vide en cas d'erreur
        }
        return response;
    }


    @Override
    public AApiResponse<Culture> getCultureById(Long id) {
        AApiResponse<Culture> response = new AApiResponse<>();
        try {
            Culture culture = cultureRepository.findById(id).orElse(null);
            if (culture != null) {
                List<Culture> data = new ArrayList<>();
                data.add(culture);
                response.setData(data);
                response.setResponseCode(200);
                response.setMessage("Culture retrieved successfully");
            } else {
                response.setResponseCode(404);
                response.setMessage("Culture not found");
            }
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    @Override
    public AApiResponse<Culture> updateCulture(Long id, Culture culture) {
        AApiResponse<Culture> response = new AApiResponse<>();
        try {
            if (cultureRepository.existsById(id)) {
                culture.setId(id);
                Culture updatedCulture = cultureRepository.save(culture);
                List<Culture> data = new ArrayList<>();
                data.add(updatedCulture);
                response.setData(data);
                response.setResponseCode(200);
                response.setMessage("Culture updated successfully");
            } else {
                response.setResponseCode(404);
                response.setMessage("Culture not found");
            }
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    @Override
    public AApiResponse<Culture> deleteCulture(Long id) {
        AApiResponse<Culture> response = new AApiResponse<>();
        try {
            if (cultureRepository.existsById(id)) {
                cultureRepository.deleteById(id);
                response.setResponseCode(200);
                response.setMessage("Culture deleted successfully");
            } else {
                response.setResponseCode(404);
                response.setMessage("Culture not found");
            }
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    @Override
    public AApiResponse<List<Culture>> getCulturesByCodePap(String codePap, int offset, int max) {
        AApiResponse<List<Culture>> response = new AApiResponse<>();
        response.setOffset(offset);
        response.setMax(max);

        try {
            List<Culture> culturesList = cultureRepository.findByCodePap(codePap);

            if (culturesList.isEmpty()) {
                response.setResponseCode(200);
                response.setMessage("Aucune culture trouvée pour le codePap : " + codePap);
                response.setData(null);
                response.setLength(0);
            } else {
                response.setResponseCode(200);
                response.setMessage("Cultures récupérées avec succès.");
                response.setData(Collections.singletonList(culturesList));
                response.setLength(culturesList.size());
            }

        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Erreur lors de la récupération des cultures : " + e.getMessage());
            response.setData(null);
            response.setLength(0);
        }

        return response;
    }
}
