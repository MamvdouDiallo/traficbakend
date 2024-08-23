package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.entities.Batiment;
import com.itma.gestionProjet.entities.DescriptionEquipement;
import com.itma.gestionProjet.entities.PersonneAffecte;
import com.itma.gestionProjet.repositories.DescriptionEquipementRepository;
import com.itma.gestionProjet.repositories.PersonneAffecteRepository;
import com.itma.gestionProjet.requests.DescriptionEquipementRequest;
import com.itma.gestionProjet.services.DescriptionEquipementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DescriptionEquipementServiceImpl implements DescriptionEquipementService {

    @Autowired
    private DescriptionEquipementRepository descriptionEquipementRepository;


    @Autowired
    private PersonneAffecteRepository personneAffecteRepository;

    @Override
    public List<DescriptionEquipement> createDescriptionEquipement(List<DescriptionEquipementRequest> descriptionEquipementRequests) {
        List<DescriptionEquipement> createdDescriptionEquipements = new ArrayList<>();

        try {
            for (DescriptionEquipementRequest request : descriptionEquipementRequests) {
                // Vérifiez si PersonneAffecte existe avec le codePap donné
                PersonneAffecte personneAffecte = (PersonneAffecte) personneAffecteRepository.findByCodePap(request.getCodePap())
                        .orElseThrow(() -> new RuntimeException("PersonneAffecte avec codePap " + request.getCodePap() + " non trouvée"));

                // Créez une nouvelle instance de DescriptionEquipement
                DescriptionEquipement descriptionEquipement = new DescriptionEquipement();
                descriptionEquipement.setGeo(request.getGeo());
                descriptionEquipement.setCodePap(request.getCodePap());
                descriptionEquipement.setCodeEquipement(request.getCodeEquipement());
                descriptionEquipement.setNombreEquipement(request.getNombreEquipement());
                descriptionEquipement.setCount(request.getCount());
                descriptionEquipement.setPhoto(request.getPhoto());
                descriptionEquipement.setTypeEquipement(request.getTypeEquipement());
                descriptionEquipement.setEtatEquipement(request.getEtatEquipement());
                descriptionEquipement.setProprietaire(request.getProprietaire());
                descriptionEquipement.setInfoComplementaire(request.getInfoComplementaire());
                descriptionEquipement.setParentcle(request.getParentKey());
                descriptionEquipement.setCle(request.getKey());
                descriptionEquipement.setChildcle(request.getChildKey());
                descriptionEquipement.setPersonneAffecte(personneAffecte);

                // Sauvegardez l'objet DescriptionEquipement dans la base de données
                DescriptionEquipement savedDescriptionEquipement = descriptionEquipementRepository.save(descriptionEquipement);

                // Ajoutez l'objet sauvegardé à la liste des équipements créés
                createdDescriptionEquipements.add(savedDescriptionEquipement);
            }
        } catch (RuntimeException e) {
            // Gérez l'exception ici, comme le lancement d'une exception personnalisée ou le retour d'une réponse d'erreur
            throw e;  // Vous pouvez aussi re-lancer l'exception pour qu'elle soit captée par le contrôleur
        } catch (Exception e) {
            e.printStackTrace();
            // Gérez les exceptions appropriées ici, comme le lancement d'une exception personnalisée ou le retour d'une réponse d'erreur.
        }

        return createdDescriptionEquipements;
    }


    @Override
    public AApiResponse<DescriptionEquipement> getAllDescriptionEquipements(int page, int size) {
        AApiResponse<DescriptionEquipement> response = new AApiResponse<>();
        try {
            Page<DescriptionEquipement> paginated = descriptionEquipementRepository.findAll(PageRequest.of(page, size));
            response.setData(paginated.getContent());
            response.setOffset(page);
            response.setMax(size);
            response.setLength(paginated.getTotalElements());
            response.setResponseCode(200);
            response.setMessage("Equipements retrieved successfully");
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    @Override
    public AApiResponse<DescriptionEquipement> getDescriptionEquipementById(Long id) {
        AApiResponse<DescriptionEquipement> response = new AApiResponse<>();
        try {
            DescriptionEquipement equipement = descriptionEquipementRepository.findById(id).orElse(null);
            if (equipement != null) {
                List<DescriptionEquipement> data = new ArrayList<>();
                data.add(equipement);
                response.setData(data);
                response.setResponseCode(200);
                response.setMessage("Equipement retrieved successfully");
            } else {
                response.setResponseCode(404);
                response.setMessage("Equipement not found");
            }
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    @Override
    public AApiResponse<DescriptionEquipement> updateDescriptionEquipement(Long id, DescriptionEquipement descriptionEquipement) {
        AApiResponse<DescriptionEquipement> response = new AApiResponse<>();
        try {
            if (descriptionEquipementRepository.existsById(id)) {
                descriptionEquipement.setId(id);
                DescriptionEquipement updatedEquipement = descriptionEquipementRepository.save(descriptionEquipement);
                List<DescriptionEquipement> data = new ArrayList<>();
                data.add(updatedEquipement);
                response.setData(data);
                response.setResponseCode(200);
                response.setMessage("Equipement updated successfully");
            } else {
                response.setResponseCode(404);
                response.setMessage("Equipement not found");
            }
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    @Override
    public AApiResponse<DescriptionEquipement> deleteDescriptionEquipement(Long id) {
        AApiResponse<DescriptionEquipement> response = new AApiResponse<>();
        try {
            if (descriptionEquipementRepository.existsById(id)) {
                descriptionEquipementRepository.deleteById(id);
                response.setResponseCode(200);
                response.setMessage("Equipement deleted successfully");
            } else {
                response.setResponseCode(404);
                response.setMessage("Equipement not found");
            }
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    @Override
    public AApiResponse<List<DescriptionEquipement>> getEquipementByCodePap(String codePap, int offset, int max) {
        List<DescriptionEquipement> descriptionEquipements = descriptionEquipementRepository.findByCodePap(codePap);

        AApiResponse<List<DescriptionEquipement>> response = new AApiResponse<>();
        response.setOffset(offset);
        response.setMax(max);

        if (descriptionEquipements.isEmpty()) {
            response.setResponseCode(200);
            response.setMessage("Aucun bâtiment trouvé pour le codePap : " + codePap);
            response.setData(null);
            response.setLength(0);
        } else {
            response.setResponseCode(200);
            response.setMessage("Bâtiments récupérés avec succès.");
            response.setData(Collections.singletonList(descriptionEquipements));
            response.setLength(descriptionEquipements.size());
        }

        return response;
    }
}
