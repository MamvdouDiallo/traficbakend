package com.itma.gestionProjet.seeders;

import com.itma.gestionProjet.entities.Role;
import com.itma.gestionProjet.repositories.PersonneAffecteRepository;
import com.itma.gestionProjet.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

@Component
@Order(1)
public class RoleSeeder implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        String[] roles = { "Super Admin", "Representant principal","Admin","User","Maitre d'ouvrage","Consultant" };
        for (String roleName : roles) {
            if (!roleRepository.existsByName(roleName)) {
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


