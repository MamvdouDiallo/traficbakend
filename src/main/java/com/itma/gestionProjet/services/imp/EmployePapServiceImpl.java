package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.EmployePapDTO;
import com.itma.gestionProjet.entities.Coproprietaire;
import com.itma.gestionProjet.entities.EmployePap;
import com.itma.gestionProjet.entities.PersonneAffecte;
import com.itma.gestionProjet.repositories.EmployePapRepository;
import com.itma.gestionProjet.repositories.PersonneAffecteRepository;
import com.itma.gestionProjet.requests.EmployePapRequest;
import com.itma.gestionProjet.services.EmployePapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EmployePapServiceImpl implements EmployePapService {

    @Autowired
    private EmployePapRepository employePapRepository;

    @Autowired
    private PersonneAffecteRepository personneAffecteRepository;



    @Override
    public AApiResponse<EmployePap> getAllEmployePaps(Pageable pageable) {
        Page<EmployePap> page = employePapRepository.findAll(pageable);
        AApiResponse<EmployePap> response = new AApiResponse<>();
        response.setResponseCode(200);
        response.setData(page.getContent());
        response.setOffset(page.getNumber());
        response.setMax(page.getSize());
        response.setLength(page.getTotalElements());
        response.setMessage("Liste des EmployePaps récupérée avec succès.");
        return response;
    }

    @Override
    public List<EmployePap> createEmployePap(List<EmployePapRequest> employePapRequests) {
        List<EmployePap> createdEmployePaps = new ArrayList<>();

        try {
            for (EmployePapRequest request : employePapRequests) {
                // Vérifiez si PersonneAffecte existe avec le codePap donné
                PersonneAffecte personneAffecte = (PersonneAffecte) personneAffecteRepository.findByCodePap(request.getCodePap())
                        .orElseThrow(() -> new RuntimeException("PersonneAffecte avec codePap " + request.getCodePap() + " non trouvée"));

                // Créez une nouvelle instance d'EmployePap
                EmployePap employePap = new EmployePap();
                employePap.setCodePap(request.getCodePap());
                employePap.setCodeEmploye(request.getCodeEmploye());
                employePap.setNumeroIdentifiant(request.getNumeroIdentifiant());
                employePap.setPrenomNom(request.getPrenomNom());
                employePap.setContactTelephonique(request.getContactTelephonique());
                employePap.setCategorieActivite(request.getCategorieActivite());
                employePap.setSexe(request.getSexe());
                employePap.setAge(request.getAge());
                employePap.setNationalite(request.getNationalite());
                employePap.setQualiteEmploye(request.getQualiteEmploye());
                employePap.setRemuneration(request.getRemuneration());
                employePap.setPrime(request.getPrime());
                employePap.setHandicap(request.getHandicap());
                employePap.setParentcle(request.getParentcle());
                employePap.setCle(request.getCle());
                employePap.setChildcle(request.getChildcle());
                employePap.setInfoComplementaire(request.getInfoComplementaire());
                employePap.setPersonneAffecte(personneAffecte);

                // Sauvegardez l'objet EmployePap dans la base de données
                EmployePap savedEmployePap = employePapRepository.save(employePap);

                // Ajoutez l'entité sauvegardée à la liste
                createdEmployePaps.add(savedEmployePap);
            }

        } catch (Exception e) {
            // Gérez les exceptions appropriées
            e.printStackTrace();
            // Vous pouvez également lancer une exception personnalisée ou renvoyer une réponse d'erreur
        }

        return createdEmployePaps;
    }





    @Override
    public EmployePap updateEmployePap(Long id, EmployePap employePap) {
        if (!employePapRepository.existsById(id)) {
            throw new RuntimeException("EmployePap non trouvé.");
        }
        employePap.setId(id);
        return employePapRepository.save(employePap);
    }

    @Override
    public void deleteEmployePap(Long id) {
        if (!employePapRepository.existsById(id)) {
            throw new RuntimeException("EmployePap non trouvé.");
        }
        employePapRepository.deleteById(id);
    }

    @Override
    public AApiResponse<List<EmployePap>> getEmployesByCodePap(String codePap, int offset, int max) {
        AApiResponse<List<EmployePap>> response = new AApiResponse<>();
        response.setOffset(offset);
        response.setMax(max);

        try {
            List<EmployePap> employes = employePapRepository.findByCodePap(codePap);

            if (employes.isEmpty()) {
                response.setResponseCode(404);
                response.setMessage("Aucun employé trouvé pour le codePap : " + codePap);
                response.setData(null);
                response.setLength(0);
            } else {
                response.setResponseCode(200);
                response.setMessage("Employés récupérés avec succès.");
                response.setData(Collections.singletonList(employes));
                response.setLength(employes.size());
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
