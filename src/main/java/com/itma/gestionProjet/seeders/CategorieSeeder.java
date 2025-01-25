package com.itma.gestionProjet.seeders;

import com.itma.gestionProjet.entities.Categorie;
import com.itma.gestionProjet.repositories.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class CategorieSeeder implements CommandLineRunner {

    @Autowired
    private CategorieRepository categorieRepository;

    @Override
    public void run(String... args) throws Exception {
        String[] categories = { "Niveau 1", "Niveau 2", "Niveau 3" };
        for (String categoryName : categories) {
            if (!categorieRepository.existsByLibelle(categoryName)) {
                Categorie categorie = new Categorie();
                categorie.setLibelle(categoryName);
                categorieRepository.save(categorie);
                System.out.println("Catégorie ajoutée : " + categoryName);
            } else {
                System.out.println("Catégorie déjà existante : " + categoryName);
            }
        }
    }
}
