package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.LettreConsentement;
import com.itma.gestionProjet.entities.PersonneAffecte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LettreConsentementRepository extends JpaRepository<LettreConsentement, Long> {
    LettreConsentement findByPersonneAffecte(PersonneAffecte personneAffecte);
}
