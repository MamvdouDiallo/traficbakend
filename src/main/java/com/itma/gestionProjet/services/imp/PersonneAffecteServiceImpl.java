package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.entities.Pays;
import com.itma.gestionProjet.entities.PersonneAffecte;
import com.itma.gestionProjet.repositories.PersonneAffecteRepository;
import com.itma.gestionProjet.services.IPersonneAffecteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonneAffecteServiceImpl implements IPersonneAffecteService {

    @Autowired
    private PersonneAffecteRepository personneAffecteRepository;

    @Override
    public List<PersonneAffecte> getAllPersonneAffectes() {
        return personneAffecteRepository.findAll();
    }

    @Override
    public Optional<PersonneAffecte> getPersonneAffecteById(Long id) {
        return personneAffecteRepository.findById(id);
    }

    @Override
    public PersonneAffecte savePersonneAffecte(PersonneAffecte personneAffecte) {
        return personneAffecteRepository.save(personneAffecte);
    }

    @Override
    public void deletePersonneAffecte(Long id) {
        personneAffecteRepository.deleteById(id);
    }

    public Page<PersonneAffecte> getPersonneAffectes(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return personneAffecteRepository.findAll(pageable);
    }

    public List<PersonneAffecte> savePersonneAffectes(List<PersonneAffecte> personneAffectes) {
        return personneAffecteRepository.saveAll(personneAffectes);
    }

}