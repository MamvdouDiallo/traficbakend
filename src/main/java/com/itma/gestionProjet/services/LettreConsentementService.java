package com.itma.gestionProjet.services;

import com.itma.gestionProjet.entities.LettreConsentement;
import com.itma.gestionProjet.entities.PersonneAffecte;

import java.util.List;

public interface LettreConsentementService {
    List<LettreConsentement> getAllLettresConsentement();
    LettreConsentement getLettreConsentementById(Long id);
    LettreConsentement createLettreConsentement(LettreConsentement lettreConsentement);
    LettreConsentement updateLettreConsentement(Long id, LettreConsentement lettreConsentement);
    void deleteLettreConsentement(Long id);

    LettreConsentement getLettreConsentementByPersonneAffecte(PersonneAffecte personneAffecte);
}
