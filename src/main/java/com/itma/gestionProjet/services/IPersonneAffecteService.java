package com.itma.gestionProjet.services;

import com.itma.gestionProjet.entities.PersonneAffecte;

import java.util.List;
import java.util.Optional;

public interface IPersonneAffecteService {

    List<PersonneAffecte> getAllPersonneAffectes();
    Optional<PersonneAffecte> getPersonneAffecteById(Long id);
    PersonneAffecte savePersonneAffecte(PersonneAffecte personneAffecte);
    void deletePersonneAffecte(Long id);
}
