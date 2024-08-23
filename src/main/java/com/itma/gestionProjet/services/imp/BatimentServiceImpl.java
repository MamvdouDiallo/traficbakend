package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.entities.Batiment;
import com.itma.gestionProjet.entities.PersonneAffecte;
import com.itma.gestionProjet.repositories.BatimentRepository;
import com.itma.gestionProjet.repositories.PersonneAffecteRepository;
import com.itma.gestionProjet.requests.RequestBatiment;
import com.itma.gestionProjet.services.BatimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class BatimentServiceImpl implements BatimentService {

    @Autowired
    private BatimentRepository batimentRepository;

    @Autowired
    private PersonneAffecteRepository personneAffecteRepository;

    @Override
    public AApiResponse<Batiment> getAllBatiments(int offset, int max) {
        AApiResponse<Batiment> response = new AApiResponse<>();
        try {
            List<Batiment> batiments = batimentRepository.findAll(PageRequest.of(offset, max)).getContent();
            response.setResponseCode(200);
            response.setData(batiments);
            response.setOffset(offset);
            response.setMax(max);
            response.setMessage("Liste des bâtiments récupérée avec succès");
            response.setLength(batimentRepository.count());
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Erreur lors de la récupération des bâtiments : " + e.getMessage());
            response.setData(new ArrayList<>()); // Retourne une liste vide en cas d'erreur
        }
        return response;
    }

    @Override
    public Batiment createBatiment(List<RequestBatiment> requests) {
        Batiment batiment = null;

        for (RequestBatiment request : requests) {
            // Trouver PersonneAffecte par codePap
            PersonneAffecte personneAffecte = (PersonneAffecte) personneAffecteRepository.findByCodePap(request.getCodePap())
                    .orElseThrow(() -> new RuntimeException("PersonneAffecte avec codePap " + request.getCodePap() + " non trouvée"));

            batiment = new Batiment();
            batiment.setGeo(request.getGeo());
            batiment.setCodePap(request.getCodePap());
            batiment.setCodeRevise(request.getCodeRevise());
            batiment.setCodeBatiment(request.getCodeBatiment());
            batiment.setContour(request.getContour());
            batiment.setTypeBatiment(request.getTypeBatiment());
            batiment.setNombrePiece(request.getNombrePiece());
            batiment.setNombrePieceUtilise(request.getNombrePieceUtilise());
            batiment.setEtatBatiment(request.getEtatBatiment());
            batiment.setNatureSol(request.getNatureSol());
            batiment.setStructureMur(request.getStructureMur());
            batiment.setTypeToiture(request.getTypeToiture());
            batiment.setAutreTypeToiture(request.getAutreTypeToiture());
            batiment.setPorteFentre(request.getPorteFentre());
            batiment.setPropriete(request.getPropriete());
            batiment.setInfoComplementaire(request.getInfoComplementaire());
            batiment.setDecriptionBatiment(request.getDecriptionBatiment());
            batiment.setParentcle(request.getParentcle());
            batiment.setCle(request.getCle());
            batiment.setChildcle(request.getChildcle());
            batiment.setPersonneAffecte(personneAffecte);

            // Sauvegarder le Batiment dans la base de données
            batiment = batimentRepository.save(batiment);
        }

        return batiment;
    }
    @Override
    public AApiResponse<Batiment> getBatimentById(Long id) {
        AApiResponse<Batiment> response = new AApiResponse<>();
        try {
            Optional<Batiment> batiment = batimentRepository.findById(id);
            if (batiment.isPresent()) {
                List<Batiment> data = new ArrayList<>();
                data.add(batiment.get());
                response.setResponseCode(200);
                response.setData(data);
                response.setMessage("Bâtiment trouvé avec succès");
                response.setLength(1);
            } else {
                response.setResponseCode(404);
                response.setMessage("Bâtiment non trouvé");
            }
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Erreur lors de la récupération du bâtiment : " + e.getMessage());
            response.setData(new ArrayList<>()); // Retourne une liste vide en cas d'erreur
        }
        return response;
    }

    @Override
    public AApiResponse<Batiment> updateBatiment(Long id, Batiment batiment) {
        AApiResponse<Batiment> response = new AApiResponse<>();
        try {
            Optional<Batiment> existingBatiment = batimentRepository.findById(id);
            if (existingBatiment.isPresent()) {
                Batiment updatedBatiment = existingBatiment.get();
                // Mettre à jour les champs nécessaires
                updatedBatiment.setGeo(batiment.getGeo());
                updatedBatiment.setCodeRevise(batiment.getCodeRevise());
                updatedBatiment.setCodeBatiment(batiment.getCodeBatiment());
                // Ajouter d'autres mises à jour ici...

                Batiment savedBatiment = batimentRepository.save(updatedBatiment);
                List<Batiment> data = new ArrayList<>();
                data.add(savedBatiment);
                response.setResponseCode(200);
                response.setData(data);
                response.setMessage("Bâtiment mis à jour avec succès");
                response.setLength(1);
            } else {
                response.setResponseCode(404);
                response.setMessage("Bâtiment non trouvé");
            }
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Erreur lors de la mise à jour du bâtiment : " + e.getMessage());
            response.setData(new ArrayList<>()); // Retourne une liste vide en cas d'erreur
        }
        return response;
    }

    @Override
    public AApiResponse<Void> deleteBatiment(Long id) {
        AApiResponse<Void> response = new AApiResponse<>();
        try {
            batimentRepository.deleteById(id);
            response.setResponseCode(204);
            response.setMessage("Bâtiment supprimé avec succès");
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Erreur lors de la suppression du bâtiment : " + e.getMessage());
        }
        return response;
    }

    @Override
    public AApiResponse<List<Batiment>> getBatimentsByCodePap(String codePap, int offset, int max) {
        List<Batiment> batiments = batimentRepository.findByCodePap(codePap);

        AApiResponse<List<Batiment>> response = new AApiResponse<>();
        response.setOffset(offset);
        response.setMax(max);

        if (batiments.isEmpty()) {
            response.setResponseCode(200);
            response.setMessage("Aucun bâtiment trouvé pour le codePap : " + codePap);
            response.setData(null);
            response.setLength(0);
        } else {
            response.setResponseCode(200);
            response.setMessage("Bâtiments récupérés avec succès.");
            response.setData(Collections.singletonList(batiments));
            response.setLength(batiments.size());
        }

        return response;
    }
}
