package com.itma.gestionProjet.services;

import com.itma.gestionProjet.dtos.PersonneAffecteDTO;
import com.itma.gestionProjet.entities.PersonneAffecte;

import java.util.List;
import java.util.Optional;

public interface IPersonneAffecteService {

    List<PersonneAffecte> getAllPersonneAffectes();
    Optional<PersonneAffecte> getPersonneAffecteById(Long id);
    PersonneAffecteDTO savePersonneAffecte(PersonneAffecteDTO personneAffecte);

    PersonneAffecteDTO updatePersonneAffecte(Long id, PersonneAffecteDTO personneAffecteDetails);

    void deletePersonneAffecte(Long id);
}
