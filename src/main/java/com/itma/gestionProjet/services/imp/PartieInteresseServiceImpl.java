package com.itma.gestionProjet.services.imp;


import com.itma.gestionProjet.dtos.ContactDTO;
import com.itma.gestionProjet.dtos.PartieInteresseDTO;
import com.itma.gestionProjet.dtos.PartieInteresseResponseDTO;
import com.itma.gestionProjet.entities.*;
import com.itma.gestionProjet.exceptions.ContactMobileAlreadyExistsException;
import com.itma.gestionProjet.exceptions.EmailAlreadyExistsException;
import com.itma.gestionProjet.exceptions.PartieInteresseAlreadyExistsException;
import com.itma.gestionProjet.exceptions.PartieInteresseNotFoundException;
import com.itma.gestionProjet.repositories.*;
import com.itma.gestionProjet.services.PartieInteresseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PartieInteresseServiceImpl implements PartieInteresseService {

    @Autowired
    private PartieInteresseRepository repository;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;


    @Autowired
    private CategoriePartieInteresseRepository categoriePartieInteresseRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<PartieInteresse> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<PartieInteresse> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public PartieInteresse save(PartieInteresseResponseDTO partieInteresse) {
        // Vérifier si une PartieInteresse avec le même libellé existe déjà
        Optional<PartieInteresse> optionalPip = repository.findByLibelle(partieInteresse.getLibelle());
        if (optionalPip.isPresent()) {
            throw new PartieInteresseAlreadyExistsException("Partie intéressée avec ce même libellé existe déjà !");
        }

        Project defaultProject = projectRepository.findById((long) partieInteresse.getProject_id())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Créer la nouvelle PartieInteresse
        PartieInteresse pip = new PartieInteresse();
        pip.setLibelle(partieInteresse.getLibelle());
        pip.setAdresse(partieInteresse.getAdresse());
        pip.setLocalisation(partieInteresse.getLocalisation());
        pip.setCourrielPrincipal(partieInteresse.getCourielPrincipal());
        pip.setStatut(partieInteresse.getStatut());
        pip.setNormes(partieInteresse.getNormes());

        // Assigner la catégorie
        Optional<CategoriePartieInteresse> cpip = categoriePartieInteresseRepository.findById(partieInteresse.getCategoriePartieInteresse());
        cpip.ifPresent(pip::setCategoriePartieInteresse);

        pip.setProject(defaultProject);

        // Parcourir les contacts dans la clé contacts
        List<ContactDTO> contacts = partieInteresse.getContacts();
        List<User> users = new ArrayList<>();

        for (ContactDTO contact : contacts) {
            // Créer un utilisateur pour chaque contact
            User newUser = new User();
            newUser.setEmail(contact.getEmailContactPrincipal());
            newUser.setLastname(contact.getNomContactPrincipal());
            newUser.setFirstname(contact.getPrenomContactPrincipal());
            newUser.setContact(contact.getTelephoneContactPrincipal());
            newUser.setLocality(contact.getAdresseContactPrincipal());
            newUser.setSexe(contact.getSexeContactPrincipal());
            newUser.setEnabled(true);
            newUser.setPassword(bCryptPasswordEncoder.encode("P@sser123"));

            // Assigner le rôle à l'utilisateur
            Role role = roleRepository.findRoleByName("Representant");
            List<Role> roles = new ArrayList<>();
            roles.add(role);
            newUser.setRoles(roles);

            // Sauvegarder l'utilisateur
            userRepository.save(newUser);

            // Ajouter l'utilisateur à la liste des utilisateurs
            users.add(newUser);
        }

        // Assigner les contacts à la PartieInteresse
        pip.setContacts(users);

        // Sauvegarder la PartieInteresse avec ses contacts
        return repository.save(pip);
    }



    /*
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
*/

    @Override
    public Page<PartieInteresse> getPartieInteresses(Pageable pageable) {
        return repository.findAll(pageable);
    }


    public Page<PartieInteresse> findByCategoriePartieInteresseLibelle(String libelle, Pageable pageable) {
        return repository.findByCategoriePartieInteresseLibelle(libelle, pageable);
    }


    @Override
    public PartieInteresse update(Long id, PartieInteresseResponseDTO partieInteresseDTO) {
        // Vérification si la PartieInteresse existe
        Optional<PartieInteresse> optionalPip = repository.findById(id);
        if (!optionalPip.isPresent()) {
            throw new PartieInteresseNotFoundException("Partie intéressée avec ID " + id + " n'existe pas.");
        }
        PartieInteresse pip = optionalPip.get();

        // Mise à jour des informations de PartieInteresse (informations générales)
        pip.setLibelle(partieInteresseDTO.getLibelle());
        pip.setAdresse(partieInteresseDTO.getAdresse());
        pip.setLocalisation(partieInteresseDTO.getLocalisation());
        pip.setCourrielPrincipal(partieInteresseDTO.getCourielPrincipal());
        pip.setNormes(partieInteresseDTO.getNormes());
        pip.setStatut(partieInteresseDTO.getStatut());

        // Mise à jour de la catégorie
        Optional<CategoriePartieInteresse> cpip = categoriePartieInteresseRepository.findById(partieInteresseDTO.getCategoriePartieInteresse());
        cpip.ifPresent(pip::setCategoriePartieInteresse);

        // Gestion des contacts
        List<User> contacts = new ArrayList<>(); // Liste des contacts (représentants)

        // Si des contacts existent dans le DTO, les traiter
        if (partieInteresseDTO.getContacts() != null && !partieInteresseDTO.getContacts().isEmpty()) {
            for (ContactDTO contactDTO : partieInteresseDTO.getContacts()) {
                // Rechercher si un contact existe déjà (par exemple via l'email ou un autre identifiant unique)
                Optional<User> existingContactOpt = userRepository.findByEmail(contactDTO.getEmailContactPrincipal());
                User user;
                if (existingContactOpt.isPresent()) {
                    user = existingContactOpt.get();
                    user.setEmail(contactDTO.getEmailContactPrincipal());
                    user.setLastname(contactDTO.getNomContactPrincipal());
                    user.setFirstname(contactDTO.getPrenomContactPrincipal());
                    user.setContact(contactDTO.getTelephoneContactPrincipal());
                    user.setLocality(contactDTO.getAdresseContactPrincipal());
                    user.setSexe(contactDTO.getSexeContactPrincipal());
                    user.setEnabled(true);  // Activer le contact
                    user.setPassword(bCryptPasswordEncoder.encode("P@sser123")); // Nouveau mot de passe par défaut

                    // Mise à jour des rôles
                    Role role = roleRepository.findRoleByName("Representant principal");
                    List<Role> roles = new ArrayList<>();
                    roles.add(role);
                    user.setRoles(roles);
                    userRepository.save(user);
                } else {
                    // Si le contact n'existe pas, créer un nouveau contact
                    user = new User();
                    user.setEmail(contactDTO.getEmailContactPrincipal());
                    user.setLastname(contactDTO.getNomContactPrincipal());
                    user.setFirstname(contactDTO.getPrenomContactPrincipal());
                    user.setContact(contactDTO.getTelephoneContactPrincipal());
                    user.setLocality(contactDTO.getAdresseContactPrincipal());
                    user.setSexe(contactDTO.getSexeContactPrincipal());
                    user.setEnabled(true);
                    Role role = roleRepository.findRoleByName("Representant principal");
                    List<Role> roles = new ArrayList<>();
                    roles.add(role);
                    user.setRoles(roles);
                    userRepository.save(user);
                }
                contacts.add(user);
            }
        }
        pip.setContacts(contacts);
        return repository.save(pip);
    }




    @Override
    public void deleteById(Long id) {
        Optional<PartieInteresse> optionalPip = repository.findById(id);
        if (optionalPip.isPresent()) {
            PartieInteresse pip = optionalPip.get();
          //  User user = pip.getUser();

            repository.delete(pip); // Supprime la partie intéressée
        } else {
            throw new PartieInteresseNotFoundException("Partie intéressée non trouvée avec l'ID : " + id);
        }
    }
}

