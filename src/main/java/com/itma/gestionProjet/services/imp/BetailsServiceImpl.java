package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.BetailsDTO;
import com.itma.gestionProjet.entities.Betails;
import com.itma.gestionProjet.entities.PersonneAffecte;
import com.itma.gestionProjet.repositories.BetailsRepository;
import com.itma.gestionProjet.repositories.PersonneAffecteRepository;
import com.itma.gestionProjet.requests.BetailsRequest;
import com.itma.gestionProjet.services.BetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BetailsServiceImpl implements BetailsService {

    @Autowired
    private BetailsRepository betailsRepository;

    @Autowired
    private PersonneAffecteRepository personneAffecteRepository;

    @Override
    public List<BetailsDTO> createBetails(List<BetailsRequest> requests) {
        List<BetailsDTO> betailsDTOs = new ArrayList<>();

        for (BetailsRequest request : requests) {
            // Trouver PersonneAffecte par codePap
            PersonneAffecte personneAffecte = (PersonneAffecte) personneAffecteRepository.findByCodePap(request.getCodePap())
                    .orElseThrow(() -> new RuntimeException("PersonneAffecte avec codePap " + request.getCodePap() + " non trouvée"));

            // Créer et sauvegarder Betails
            Betails betails = new Betails();
            betails.setCodePap(request.getCodePap());
            betails.setTypeBetails(request.getTypeBetails());
            betails.setDescription(request.getDescription());
            betails.setPersonneAffecte(personneAffecte);

            Betails savedBetails = betailsRepository.save(betails);

            // Convertir en DTO
            BetailsDTO dto = new BetailsDTO();
            dto.setId(savedBetails.getId());
            dto.setCodePap(savedBetails.getCodePap());
            dto.setTypeBetails(savedBetails.getTypeBetails());
            dto.setDescription(savedBetails.getDescription());

            betailsDTOs.add(dto);
        }

        return betailsDTOs;
    }



    @Override
    public AApiResponse<Betails> getAllBetails(int page, int size) {
        AApiResponse<Betails> response = new AApiResponse<>();
        try {
            Page<Betails> paginated = betailsRepository.findAll(PageRequest.of(page, size));
            response.setData(paginated.getContent());
            response.setOffset(page);
            response.setMax(size);
            response.setLength(paginated.getTotalElements());
            response.setResponseCode(200);
            response.setMessage("Betails retrieved successfully");
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    @Override
    public AApiResponse<Betails> getBetailsById(Long id) {
        AApiResponse<Betails> response = new AApiResponse<>();
        try {
            Betails betails = betailsRepository.findById(id).orElse(null);
            if (betails != null) {
                List<Betails> data = new ArrayList<>();
                data.add(betails);
                response.setData(data);
                response.setResponseCode(200);
                response.setMessage("Betails retrieved successfully");
            } else {
                response.setResponseCode(404);
                response.setMessage("Betails not found");
            }
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    @Override
    public AApiResponse<Betails> updateBetails(Long id, Betails betails) {
        AApiResponse<Betails> response = new AApiResponse<>();
        try {
            if (betailsRepository.existsById(id)) {
                betails.setId(id);
                Betails updatedBetails = betailsRepository.save(betails);
                List<Betails> data = new ArrayList<>();
                data.add(updatedBetails);
                response.setData(data);
                response.setResponseCode(200);
                response.setMessage("Betails updated successfully");
            } else {
                response.setResponseCode(404);
                response.setMessage("Betails not found");
            }
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    @Override
    public AApiResponse<Betails> deleteBetails(Long id) {
        AApiResponse<Betails> response = new AApiResponse<>();
        try {
            if (betailsRepository.existsById(id)) {
                betailsRepository.deleteById(id);
                response.setResponseCode(200);
                response.setMessage("Betails deleted successfully");
            } else {
                response.setResponseCode(404);
                response.setMessage("Betails not found");
            }
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }


    @Override
    public AApiResponse<List<Betails>> getBetailsByCodePap(String codePap, int offset, int max) {
        AApiResponse<List<Betails>> response = new AApiResponse<>();
        response.setOffset(offset);
        response.setMax(max);

        try {
            List<Betails> betailsList = betailsRepository.findByCodePap(codePap);

            if (betailsList.isEmpty()) {
                response.setResponseCode(200);
                response.setMessage("Aucun betails trouvé pour le codePap : " + codePap);
                response.setData(null);
                response.setLength(0);
            } else {
                response.setResponseCode(200);
                response.setMessage("Betails récupérés avec succès.");
                response.setData(Collections.singletonList(betailsList));
                response.setLength(betailsList.size());
            }

        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Erreur lors de la récupération des betails : " + e.getMessage());
            response.setData(null);
            response.setLength(0);
        }

        return response;
    }

}
