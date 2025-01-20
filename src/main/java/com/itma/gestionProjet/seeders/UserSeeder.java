package com.itma.gestionProjet.seeders;

import com.itma.gestionProjet.entities.Role;
import com.itma.gestionProjet.entities.User;
import com.itma.gestionProjet.repositories.RoleRepository;
import com.itma.gestionProjet.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
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

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Vérification si le rôle "Super Admin" existe déjà
        Optional<Role> optionalRole = roleRepository.findByName("Super Admin");
        Role superAdminRole = optionalRole.orElse(null);  // Ou tu peux utiliser orElseThrow si tu veux lancer une exception si non trouvé

        if (superAdminRole == null) {
            System.out.println("Le rôle 'Super Admin' n'existe pas.");
            return;
        }

        String email = "salioufereya19@gmail.com.com";

        if (!userRepository.existsByEmail(email)) {
            User user = new User();
            user.setFirstname("Mamadou");
            user.setLastname("Diallo");
            user.setEmail(email);
            user.setEnabled(true);
            user.setPassword("Passer@123");  // Utiliser un mot de passe sécurisé en vrai
            user.setLocality("Locality Example");
            user.setContact("773417360");
            user.setSous_role("Administrateur principal");
            user.setImageUrl("http://example.com/image.jpg");
            user.setSexe("Masculin");

            // Assigner le rôle Super Admin à l'utilisateur
            user.setRoles(Collections.singletonList(superAdminRole));

            // Sauvegarder l'utilisateur dans la base de données
            userRepository.save(user);
            System.out.println("Utilisateur 'Super Admin' ajouté avec succès.");
        } else {
            System.out.println("L'utilisateur avec l'email '" + email + "' existe déjà.");
        }
    }
}

