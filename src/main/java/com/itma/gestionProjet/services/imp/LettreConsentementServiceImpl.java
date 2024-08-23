package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.entities.LettreConsentement;
import com.itma.gestionProjet.entities.PersonneAffecte;
import com.itma.gestionProjet.repositories.LettreConsentementRepository;
import com.itma.gestionProjet.services.LettreConsentementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LettreConsentementServiceImpl implements LettreConsentementService {

    @Autowired
    private LettreConsentementRepository lettreConsentementRepository;

    @Override
    public List<LettreConsentement> getAllLettresConsentement() {
        return lettreConsentementRepository.findAll();
    }

    @Override
    public LettreConsentement getLettreConsentementById(Long id) {
        return lettreConsentementRepository.findById(id).orElse(null);
    }

    @Override
    public LettreConsentement createLettreConsentement(LettreConsentement lettreConsentement) {
        return lettreConsentementRepository.save(lettreConsentement);
    }

    @Override
    public LettreConsentement updateLettreConsentement(Long id, LettreConsentement lettreConsentement) {
        LettreConsentement existingLettre = lettreConsentementRepository.findById(id).orElse(null);
        if (existingLettre != null) {
            existingLettre.setContenu(lettreConsentement.getContenu());
            existingLettre.setPersonneAffecte(lettreConsentement.getPersonneAffecte());
            return lettreConsentementRepository.save(existingLettre);
        }
        return null;
    }

    @Override
    public void deleteLettreConsentement(Long id) {
        lettreConsentementRepository.deleteById(id);
    }

    @Override
    public LettreConsentement getLettreConsentementByPersonneAffecte(PersonneAffecte personneAffecte) {
        return lettreConsentementRepository.findByPersonneAffecte(personneAffecte);
    }
}
