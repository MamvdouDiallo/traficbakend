package com.itma.gestionProjet.seeders;

import com.itma.gestionProjet.entities.Categorie;
import com.itma.gestionProjet.entities.Role;
import com.itma.gestionProjet.entities.User;
import com.itma.gestionProjet.repositories.CategorieRepository;
import com.itma.gestionProjet.repositories.RoleRepository;
import com.itma.gestionProjet.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;


@Component
@Order(2)
public class UserSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Vérification si le rôle "Super Admin" existe déjà
        Optional<Role> optionalRole = roleRepository.findByName("Super Admin");
        Role superAdminRole = optionalRole.orElse(null);

        if (superAdminRole == null) {
            System.out.println("Le rôle 'Super Admin' n'existe pas.");
            return;
        }

        // Vérification si la catégorie "Niveau 1" existe déjà
        Optional<Categorie> optionalCategorie = Optional.ofNullable(categorieRepository.findByLibelle("Niveau 1"));
        Categorie niveau1Categorie = optionalCategorie.orElse(null);

        // Si la catégorie n'existe pas, on la crée
        if (niveau1Categorie == null) {
            niveau1Categorie = new Categorie();
            niveau1Categorie.setLibelle("Niveau 1");
            categorieRepository.save(niveau1Categorie);
            System.out.println("Catégorie 'Niveau 1' créée.");
        }

        String email = "salioufereya19@gmail.com";

        // Vérification si l'utilisateur avec cet email existe déjà
        if (!userRepository.existsByEmail(email)) {
            User user = new User();
            user.setFirstname("Mamadou");
            user.setLastname("Diallo");
            user.setEmail(email);
            user.setEnabled(true);
            user.setPassword(bCryptPasswordEncoder.encode("Passer@123"));
            user.setLocality("Locality Example");
            user.setContact("773417360");
            //user.setSous_role("Administrateur principal");
            user.setImageUrl("http://example.com/image.jpg");
            user.setSexe("Masculin");

            user.setRoles(Collections.singletonList(superAdminRole));

            // Assigner la catégorie 'Niveau 1' à l'utilisateur
            user.setCategorie(niveau1Categorie);

            // Sauvegarder l'utilisateur dans la base de données
            userRepository.save(user);
            System.out.println("Utilisateur 'Super Admin' ajouté avec succès.");
        } else {
            System.out.println("L'utilisateur avec l'email '" + email + "' existe déjà.");
        }
    }
}

