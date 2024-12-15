package com.itma.gestionProjet.seeders;

import com.itma.gestionProjet.entities.Role;
import com.itma.gestionProjet.repositories.PersonneAffecteRepository;
import com.itma.gestionProjet.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleSeeders implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Liste des rôles à insérer
        String[] roles = { "Super Admin"};

        // Pour chaque rôle, on vérifie s'il existe, sinon on l'ajoute
        for (String roleName : roles) {
            if (!roleRepository.existsByName(roleName)) {
                // Créer et sauvegarder le rôle s'il n'existe pas
                Role role = new Role();
                role.setName(roleName);
              roleRepository.save(role);
                System.out.println("Rôle ajouté : " + roleName);
            } else {
                System.out.println("Rôle déjà existant : " + roleName);
            }
        }
    }
}
