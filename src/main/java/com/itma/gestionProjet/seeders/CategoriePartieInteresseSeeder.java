package com.itma.gestionProjet.seeders;

import com.itma.gestionProjet.entities.CategoriePartieInteresse;
import com.itma.gestionProjet.repositories.CategoriePartieInteresseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class CategoriePartieInteresseSeeder  implements CommandLineRunner {

    @Autowired
    private CategoriePartieInteresseRepository repository;

    @Override
    public void run(String... args) throws Exception {
        /*
        repository.deleteAll();
        repository.save(new CategoriePartieInteresse(null, "ONG"));
        repository.save(new CategoriePartieInteresse(null, "Organisations"));
        repository.save(new CategoriePartieInteresse(null, "Entreprises"));
        repository.save(new CategoriePartieInteresse(null, "Médias"));
        repository.save(new CategoriePartieInteresse(null, "Bailleurs"));
        repository.save(new CategoriePartieInteresse(null, "Organes & autorités adminisatratifs"));

         */
    }
}
