package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.entities.Coproprietaire;
import com.itma.gestionProjet.entities.PersonneAffecte;
import com.itma.gestionProjet.repositories.CoproprietaireRepository;
import com.itma.gestionProjet.repositories.PersonneAffecteRepository;
import com.itma.gestionProjet.requests.CoproprietaireRequest;
import com.itma.gestionProjet.services.CoproprietaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class CoproprietaireServiceImpl implements CoproprietaireService {

    @Autowired
    private CoproprietaireRepository coproprietaireRepository;

    @Autowired
    private PersonneAffecteRepository personneAffecteRepository;

    @Override
    public AApiResponse<Coproprietaire> getAllCoproprietaires(int offset, int max) {
        AApiResponse<Coproprietaire> response = new AApiResponse<>();
        try {
            List<Coproprietaire> coproprietaires = coproprietaireRepository.findAll(PageRequest.of(offset, max)).getContent();
            response.setResponseCode(200);
            response.setData(coproprietaires);
            response.setOffset(offset);
            response.setMax(max);
            response.setMessage("Liste des copropriétaires récupérée avec succès");
            response.setLength(coproprietaireRepository.count());
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Erreur lors de la récupération des copropriétaires : " + e.getMessage());
            response.setData(new ArrayList<>()); // Retourne une liste vide en cas d'erreur
        }
        return response;
    }

    @Override
    public List<CoproprietaireRequest> createCoproprietaire(List<CoproprietaireRequest> coproprietaireRequests) {
        List<Coproprietaire> savedCoproprietaires = new ArrayList<>();
        List<CoproprietaireRequest> responseRequests = new ArrayList<>();

        try {
            for (CoproprietaireRequest request : coproprietaireRequests) {
                PersonneAffecte personneAffecte = (PersonneAffecte) personneAffecteRepository.findByCodePap(request.getCodePap())
                        .orElseThrow(() -> new RuntimeException("PersonneAffecte avec codePap " + request.getCodePap() + " non trouvée"));

                Coproprietaire coproprietaire = new Coproprietaire();
                coproprietaire.setPrenomNom(request.getPrenomNom());
                coproprietaire.setContactTelephonique(request.getContactTelephonique());
                coproprietaire.setCodePap(request.getCodePap());
                coproprietaire.setCodeCoProprietaire(request.getCodeCoProprietaire());
                coproprietaire.setSexe(request.getSexe());
                coproprietaire.setAge(request.getAge());
                coproprietaire.setSituationMatrimoniale(request.getSituationMatrimoniale());
                coproprietaire.setPhoto(request.getPhoto());
                coproprietaire.setInfoComplementaire(request.getInfoComplementaire());
                coproprietaire.setPersonneAffecte(personneAffecte);

                Coproprietaire savedCoproprietaire = coproprietaireRepository.save(coproprietaire);
                savedCoproprietaires.add(savedCoproprietaire);
            }

            // Convertir les objets Coproprietaire en CoproprietaireRequest pour la réponse
            for (Coproprietaire coproprietaire : savedCoproprietaires) {
                CoproprietaireRequest responseRequest = new CoproprietaireRequest();
                responseRequest.setPrenomNom(coproprietaire.getPrenomNom());
                responseRequest.setContactTelephonique(coproprietaire.getContactTelephonique());
                responseRequest.setCodePap(coproprietaire.getCodePap());
                responseRequest.setCodeCoProprietaire(coproprietaire.getCodeCoProprietaire());
                responseRequest.setSexe(coproprietaire.getSexe());
                responseRequest.setAge(coproprietaire.getAge());
                responseRequest.setSituationMatrimoniale(coproprietaire.getSituationMatrimoniale());
                responseRequest.setPhoto(coproprietaire.getPhoto());
                responseRequest.setInfoComplementaire(coproprietaire.getInfoComplementaire());
                responseRequests.add(responseRequest);
            }

        } catch (Exception e) {
            // Gérer les exceptions appropriées
            e.printStackTrace();
        }

        return responseRequests;  // Retourner la liste des CoproprietaireRequest
    }



    @Override
    public AApiResponse<Coproprietaire> getCoproprietaireById(Long id) {
        AApiResponse<Coproprietaire> response = new AApiResponse<>();
        try {
            Optional<Coproprietaire> coproprietaire = coproprietaireRepository.findById(id);
            if (coproprietaire.isPresent()) {
                List<Coproprietaire> data = new ArrayList<>();
                data.add(coproprietaire.get());
                response.setResponseCode(200);
                response.setData(data);
                response.setMessage("Copropriétaire trouvé avec succès");
                response.setLength(1);
            } else {
                response.setResponseCode(404);
                response.setMessage("Copropriétaire non trouvé");
            }
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Erreur lors de la récupération du copropriétaire : " + e.getMessage());
            response.setData(new ArrayList<>()); // Retourne une liste vide en cas d'erreur
        }
        return response;
    }

    @Override
    public AApiResponse<Coproprietaire> updateCoproprietaire(Long id, Coproprietaire coproprietaire) {
        AApiResponse<Coproprietaire> response = new AApiResponse<>();
        try {
            Optional<Coproprietaire> existingCoproprietaire = coproprietaireRepository.findById(id);
            if (existingCoproprietaire.isPresent()) {
                Coproprietaire updatedCoproprietaire = existingCoproprietaire.get();
                // Mettre à jour les champs nécessaires
                updatedCoproprietaire.setPrenomNom(coproprietaire.getPrenomNom());
                updatedCoproprietaire.setContactTelephonique(coproprietaire.getContactTelephonique());
                updatedCoproprietaire.setCodePap(coproprietaire.getCodePap());
                updatedCoproprietaire.setCodeCoProprietaire(coproprietaire.getCodeCoProprietaire());
                updatedCoproprietaire.setSexe(coproprietaire.getSexe());
                updatedCoproprietaire.setAge(coproprietaire.getAge());
                updatedCoproprietaire.setSituationMatrimoniale(coproprietaire.getSituationMatrimoniale());
                updatedCoproprietaire.setPhoto(coproprietaire.getPhoto());
                updatedCoproprietaire.setInfoComplementaire(coproprietaire.getInfoComplementaire());
                updatedCoproprietaire.setPersonneAffecte(coproprietaire.getPersonneAffecte());

                Coproprietaire savedCoproprietaire = coproprietaireRepository.save(updatedCoproprietaire);
                List<Coproprietaire> data = new ArrayList<>();
                data.add(savedCoproprietaire);
                response.setResponseCode(200);
                response.setData(data);
                response.setMessage("Copropriétaire mis à jour avec succès");
                response.setLength(1);
            } else {
                response.setResponseCode(404);
                response.setMessage("Copropriétaire non trouvé");
            }
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Erreur lors de la mise à jour du copropriétaire : " + e.getMessage());
            response.setData(new ArrayList<>()); // Retourne une liste vide en cas d'erreur
        }
        return response;
    }

    @Override
    public AApiResponse<Void> deleteCoproprietaire(Long id) {
        AApiResponse<Void> response = new AApiResponse<>();
        try {
            Optional<Coproprietaire> existingCoproprietaire = coproprietaireRepository.findById(id);
            if (existingCoproprietaire.isPresent()) {
                coproprietaireRepository.delete(existingCoproprietaire.get());
                response.setResponseCode(200);
                response.setMessage("Copropriétaire supprimé avec succès");
            } else {
                response.setResponseCode(404);
                response.setMessage("Copropriétaire non trouvé");
            }
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Erreur lors de la suppression du copropriétaire : " + e.getMessage());
        }
        return response;
    }


    @Override
    public AApiResponse<List<Coproprietaire>> getCoproprietairesByCodePap(String codePap, int offset, int max) {
        AApiResponse<List<Coproprietaire>> response = new AApiResponse<>();
        response.setOffset(offset);
        response.setMax(max);

        try {
            List<Coproprietaire> coproprietaires = coproprietaireRepository.findByCodePap(codePap);

            if (coproprietaires.isEmpty()) {
                response.setResponseCode(404);
                response.setMessage("Aucun copropriétaire trouvé pour le codePap : " + codePap);
                response.setData(null);
                response.setLength(0);
            } else {
                response.setResponseCode(200);
                response.setMessage("Copropriétaires récupérés avec succès.");
                response.setData(Collections.singletonList(coproprietaires));
                response.setLength(coproprietaires.size());
            }

        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Erreur lors de la récupération des copropriétaires : " + e.getMessage());
            response.setData(null);
            response.setLength(0);
        }

        return response;
    }
}
