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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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

        // Liste des utilisateurs à créer
        List<UserData> usersToCreate = Arrays.asList(
                new UserData("Mamadou", "Diallo", "salioufereya19@gmail.com", "773417360", "Masculin"),
                new UserData("Adonis", "IT Mobile", "adonis@itmobileafrique.com", "771234567", "Masculin"),
                new UserData("Ndack", "IT Mobile", "ndack@itmobileafrique.com", "772345678", "Masculin"),
                new UserData("Massete", "IT Mobile", "massete@itmobileafrique.com", "773456789", "Masculin"),
                new UserData("Papa Ousseynou", "Sy", "papaousseynousy@gmail.com", "774567890", "Masculin")
        );

        // Mot de passe commun pour tous les utilisateurs
        String commonPassword = "Invodis@2024!";
        String encodedPassword = bCryptPasswordEncoder.encode(commonPassword);

        for (UserData userData : usersToCreate) {
            // Vérification si l'utilisateur avec cet email existe déjà
            if (!userRepository.existsByEmail(userData.getEmail())) {
                User user = new User();
                user.setFirstname(userData.getFirstname());
                user.setLastname(userData.getLastname());
                user.setEmail(userData.getEmail());
                user.setEnabled(true);
                user.setPassword(encodedPassword);
                user.setLocality("Dakar");
                user.setContact(userData.getContact());
                user.setImageUrl("http://example.com/image.jpg");
                user.setSexe(userData.getSexe());

                user.setRoles(Collections.singletonList(superAdminRole));
                user.setCategorie(niveau1Categorie);

                // Sauvegarder l'utilisateur dans la base de données
                userRepository.save(user);
                System.out.println("Utilisateur '" + userData.getFirstname() + " " + userData.getLastname() + "' ajouté avec succès.");
            } else {
                System.out.println("L'utilisateur avec l'email '" + userData.getEmail() + "' existe déjà.");
            }
        }

        System.out.println("Mot de passe commun pour tous les utilisateurs : " + commonPassword);
    }


    // Classe interne pour stocker les données des utilisateurs
    private static class UserData {
        private String firstname;
        private String lastname;
        private String email;
        private String contact;
        private String sexe;

        public UserData(String firstname, String lastname, String email, String contact, String sexe) {
            this.firstname = firstname;
            this.lastname = lastname;
            this.email = email;
            this.contact = contact;
            this.sexe = sexe;
        }
        public String getFirstname() { return firstname; }
        public String getLastname() { return lastname; }
        public String getEmail() { return email; }
        public String getContact() { return contact; }
        public String getSexe() { return sexe; }
    }
}