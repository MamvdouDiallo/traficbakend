package com.itma.gestionProjet.services.imp;


import com.itma.gestionProjet.dtos.PartieInteresseDTO;
import com.itma.gestionProjet.entities.*;
import com.itma.gestionProjet.exceptions.ContactMobileAlreadyExistsException;
import com.itma.gestionProjet.exceptions.EmailAlreadyExistsException;
import com.itma.gestionProjet.exceptions.PartieInteresseAlreadyExistsException;
import com.itma.gestionProjet.exceptions.PartieInteresseNotFoundException;
import com.itma.gestionProjet.repositories.CategoriePartieInteresseRepository;
import com.itma.gestionProjet.repositories.PartieInteresseRepository;
import com.itma.gestionProjet.repositories.RoleRepository;
import com.itma.gestionProjet.repositories.UserRepository;
import com.itma.gestionProjet.services.PartieInteresseService;
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

    @Override
    public PartieInteresse save(PartieInteresseDTO partieInteresse) {
        // Vérifier si une PartieInteresse avec le même libellé existe déjà
        Optional<PartieInteresse> optionalPip = repository.findByLibelle(partieInteresse.getLibelle());
        if (optionalPip.isPresent()) {
            throw new PartieInteresseAlreadyExistsException("Partie intéressée avec ce même libellé existe déjà !");
        }

        // Vérifier si un utilisateur avec le même email existe déjà


        // Vérifier si un utilisateur avec le même numéro de contact existe déjà


        // Créer et sauvegarder le nouvel utilisateur
        User newUser = new User();
        newUser.setEmail(partieInteresse.getEmailContactPrincipal());
        newUser.setLastname(partieInteresse.getNomContactPrincipal());
        newUser.setFirstname(partieInteresse.getPrenomContactPrincipal());
        newUser.setContact(partieInteresse.getTelephoneContactPrincipal());
        newUser.setLocality(partieInteresse.getAdresseContactPrincipal());
        newUser.setDate_of_birth(partieInteresse.getDateNaissanceContactPrincipal());
        newUser.setPlace_of_birth(partieInteresse.getLieuNaisasnceContactPrincipal());
        newUser.setSexe(partieInteresse.getSexeContactPrincipal());
        newUser.setEnabled(true);
        newUser.setPassword(bCryptPasswordEncoder.encode("P@sser123"));

        // Assigner le rôle à l'utilisateur
        Role role = roleRepository.findRoleByName("Representant principal");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        newUser.setRoles(roles);

        // Sauvegarder l'utilisateur
        userRepository.save(newUser);

        // Créer et sauvegarder la nouvelle PartieInteresse
        PartieInteresse pip = new PartieInteresse();
        pip.setLibelle(partieInteresse.getLibelle());
        pip.setAdresse(partieInteresse.getAdresse());
        pip.setLocalisation(partieInteresse.getLocalisation());
        pip.setCourrielPrincipal(partieInteresse.getCourielPrincipal());
        pip.setStatut(partieInteresse.getStatut());
        pip.setNormes(partieInteresse.getNormes());
        Optional<CategoriePartieInteresse> cpip = categoriePartieInteresseRepository.findById(partieInteresse.getCategoriePartieInteresse());
        cpip.ifPresent(pip::setCategoriePartieInteresse);
        pip.setUser(newUser);

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
    public PartieInteresse update(Long id, PartieInteresseDTO partieInteresse) {
        Optional<PartieInteresse> optionalPip = repository.findById(id);
        if (!optionalPip.isPresent()) {
            throw new PartieInteresseNotFoundException("Partie interéssée avec ID " + id + " n'existe pas.");
        }
        PartieInteresse pip = optionalPip.get();
        // Update user
        User user = pip.getUser();
        if (user == null) {
            user = new User();
        }
        user.setEmail(partieInteresse.getEmailContactPrincipal());
        user.setLastname(partieInteresse.getNomContactPrincipal());
        user.setFirstname(partieInteresse.getPrenomContactPrincipal());
        user.setContact(partieInteresse.getTelephoneContactPrincipal());
        user.setLocality(partieInteresse.getAdresseContactPrincipal());
        user.setDate_of_birth(partieInteresse.getDateNaissanceContactPrincipal());
        user.setPlace_of_birth(partieInteresse.getLieuNaisasnceContactPrincipal());
        user.setSexe(partieInteresse.getSexeContactPrincipal());
        user.setEnabled(true);
        // Password handling should be implemented properly
        user.setPassword(bCryptPasswordEncoder.encode("P@sser123"));
        Role role = roleRepository.findRoleByName("Representant principal");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
        // Update PartieInteresse
        pip.setLibelle(partieInteresse.getLibelle());
        pip.setAdresse(partieInteresse.getAdresse());
        pip.setLocalisation(partieInteresse.getLocalisation());
        pip.setCourrielPrincipal(partieInteresse.getCourielPrincipal());
        pip.setNormes(partieInteresse.getNormes());
        pip.setStatut(partieInteresse.getStatut());

        Optional<CategoriePartieInteresse> cpip = categoriePartieInteresseRepository.findById(partieInteresse.getCategoriePartieInteresse());
        cpip.ifPresent(pip::setCategoriePartieInteresse);
        pip.setUser(user);

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

