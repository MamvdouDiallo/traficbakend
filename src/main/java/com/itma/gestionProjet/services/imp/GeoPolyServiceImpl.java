package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.entities.EmployePap;
import com.itma.gestionProjet.entities.GeoPoly;
import com.itma.gestionProjet.entities.PersonneAffecte;
import com.itma.gestionProjet.repositories.GeoPolyRepository;
import com.itma.gestionProjet.repositories.PersonneAffecteRepository;
import com.itma.gestionProjet.requests.GeoPolyRequest;
import com.itma.gestionProjet.services.GeoPolyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GeoPolyServiceImpl implements GeoPolyService {

    @Autowired
    private GeoPolyRepository geoPolyRepository;


    @Autowired
    private PersonneAffecteRepository personneAffecteRepository;


    public List<GeoPoly> createGeoPoly(List<GeoPolyRequest> geoPolyRequests) {
        List<GeoPoly> geoPolyList = new ArrayList<>();

        for (GeoPolyRequest geoPolyRequest : geoPolyRequests) {
            // Rechercher la personne affectée par codePap
            PersonneAffecte personneAffecte = (PersonneAffecte) personneAffecteRepository.findByCodePap(geoPolyRequest.getCodePap())
                    .orElseThrow(() -> new RuntimeException("PersonneAffecte not found with codePap: " + geoPolyRequest.getCodePap()));

            // Créer l'objet GeoPoly
            GeoPoly geoPoly = new GeoPoly();
            geoPoly.setGeometrie(geoPolyRequest.getGeometrie());
            geoPoly.setUid(geoPolyRequest.getUid());
            geoPoly.setAxe(geoPolyRequest.getAxe());
            geoPoly.setCommune(geoPolyRequest.getCommune());
            geoPoly.setCodeRevise(geoPolyRequest.getCodeRevise());
            geoPoly.setCodePap(geoPolyRequest.getCodePap());
            geoPoly.setPrenomNom(geoPolyRequest.getPrenomNom());
            geoPoly.setLocalisation(geoPolyRequest.getLocalisation());
            geoPoly.setZoneRegroupement(geoPolyRequest.getZoneRegroupement());
            geoPoly.setPkZoneRegroupement(geoPolyRequest.getPkZoneRegroupement());
            geoPoly.setTypeImpact(geoPolyRequest.getTypeImpact());
            geoPoly.setInfoComplementaire(geoPolyRequest.getInfoComplementaire());

            // Associer à la personne affectée
            geoPoly.setPersonneAffecte(personneAffecte);

            // Sauvegarder l'objet GeoPoly dans la base de données
            geoPolyList.add(geoPolyRepository.save(geoPoly));
        }

        return geoPolyList;
    }


    @Override
    public AApiResponse<GeoPoly> getAllGeoPolys(int page, int size) {
        AApiResponse<GeoPoly> response = new AApiResponse<>();
        try {
            Page<GeoPoly> paginated = geoPolyRepository.findAll(PageRequest.of(page, size));
            response.setData(paginated.getContent());
            response.setOffset(page);
            response.setMax(size);
            response.setLength(paginated.getTotalElements());
            response.setResponseCode(200);
            response.setMessage("GeoPolys retrieved successfully");
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    @Override
    public AApiResponse<GeoPoly> getGeoPolyById(Long id) {
        AApiResponse<GeoPoly> response = new AApiResponse<>();
        try {
            GeoPoly geoPoly = geoPolyRepository.findById(id).orElse(null);
            if (geoPoly != null) {
                List<GeoPoly> data = new ArrayList<>();
                data.add(geoPoly);
                response.setData(data);
                response.setResponseCode(200);
                response.setMessage("GeoPoly retrieved successfully");
            } else {
                response.setResponseCode(404);
                response.setMessage("GeoPoly not found");
            }
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    @Override
    public AApiResponse<GeoPoly> updateGeoPoly(Long id, GeoPoly geoPoly) {
        AApiResponse<GeoPoly> response = new AApiResponse<>();
        try {
            if (geoPolyRepository.existsById(id)) {
                geoPoly.setId(id);
                GeoPoly updatedGeoPoly = geoPolyRepository.save(geoPoly);
                List<GeoPoly> data = new ArrayList<>();
                data.add(updatedGeoPoly);
                response.setData(data);
                response.setResponseCode(200);
                response.setMessage("GeoPoly updated successfully");
            } else {
                response.setResponseCode(404);
                response.setMessage("GeoPoly not found");
            }
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    @Override
    public AApiResponse<GeoPoly> deleteGeoPoly(Long id) {
        AApiResponse<GeoPoly> response = new AApiResponse<>();
        try {
            if (geoPolyRepository.existsById(id)) {
                geoPolyRepository.deleteById(id);
                response.setResponseCode(200);
                response.setMessage("GeoPoly deleted successfully");
            } else {
                response.setResponseCode(404);
                response.setMessage("GeoPoly not found");
            }
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }


    @Override
    public AApiResponse<List<GeoPoly>> getGeoPolyByCodePap(String codePap, int offset, int max) {
        AApiResponse<List<GeoPoly>> response = new AApiResponse<>();
        response.setOffset(offset);
        response.setMax(max);

        try {
            List<GeoPoly> geoPolies = geoPolyRepository.findByCodePap(codePap);

            if (geoPolies.isEmpty()) {
                response.setResponseCode(404);
                response.setMessage("Aucun polygone trouvé pour le codePap : " + codePap);
                response.setData(null);
                response.setLength(0);
            } else {
                response.setResponseCode(200);
                response.setMessage("PolyGone récupérés avec succès.");
                response.setData(Collections.singletonList(geoPolies));
                response.setLength(geoPolies.size());
            }

        } catch (Exception e) {
            response.setResponseCode(500);
            response.setMessage("Erreur lors de la récupération des employés : " + e.getMessage());
            response.setData(null);
            response.setLength(0);
        }

        return response;
    }
}
