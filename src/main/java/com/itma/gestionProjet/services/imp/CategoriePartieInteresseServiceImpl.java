package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.entities.CategoriePartieInteresse;
import com.itma.gestionProjet.repositories.CategoriePartieInteresseRepository;
import com.itma.gestionProjet.services.CategoriePartieInteresseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriePartieInteresseServiceImpl implements CategoriePartieInteresseService {

    @Autowired
    private CategoriePartieInteresseRepository repository;

    @Override
    public List<CategoriePartieInteresse> getAllCategories() {
        return repository.findAll();
    }

    @Override
    public CategoriePartieInteresse getCategorieById(Long id) {
        Optional<CategoriePartieInteresse> categorie = repository.findById(id);
        return categorie.orElse(null);
    }

    @Override
    public CategoriePartieInteresse createCategorie(CategoriePartieInteresse categorie) {
        return repository.save(categorie);
    }

    @Override
    public CategoriePartieInteresse updateCategorie(Long id, CategoriePartieInteresse categorie) {
        if (repository.existsById(id)) {
            categorie.setId(id);
            return repository.save(categorie);
        }
        return null;
    }

    @Override
    public void deleteCategorie(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<CategoriePartieInteresse> getCategories(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
