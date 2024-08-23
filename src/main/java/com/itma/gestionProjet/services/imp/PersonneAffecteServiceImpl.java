package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.PersonneAffecteDTO;
import com.itma.gestionProjet.entities.PartieInteresse;
import com.itma.gestionProjet.entities.PersonneAffecte;
import com.itma.gestionProjet.entities.Project;
import com.itma.gestionProjet.exceptions.PersonneAffecteAlreadyExistsException;
import com.itma.gestionProjet.repositories.PersonneAffecteRepository;
import com.itma.gestionProjet.repositories.ProjectRepository;
import com.itma.gestionProjet.services.IPersonneAffecteService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonneAffecteServiceImpl implements IPersonneAffecteService {

    @Autowired
    private PersonneAffecteRepository personneAffecteRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<PersonneAffecte> getAllPersonneAffectes() {
        return personneAffecteRepository.findAll();
    }

    @Override
    public Optional<PersonneAffecte> getPersonneAffecteById(Long id) {
        return personneAffecteRepository.findById(id);
    }

    @Transactional
    @Override
    public PersonneAffecteDTO savePersonneAffecte(PersonneAffecteDTO personneAffecte) {
        Optional<PersonneAffecte> partieInteresse=personneAffecteRepository.findByNumeroIdentification(personneAffecte.getNumeroIdentification());
        if(partieInteresse.isPresent()){
            throw new PersonneAffecteAlreadyExistsException("Ce numéro d'identification existe déjà.");
        }

        Project defaultProject = projectRepository.findById((long) personneAffecte.getProject_id()).orElseThrow(() -> new RuntimeException("Project not found"));
        PersonneAffecte personneAffecte1 = new PersonneAffecte();

        personneAffecte1.setIdPap(personneAffecte.getIdPap());
        personneAffecte1.setIdParcelle(personneAffecte.getIdParcelle());
        personneAffecte1.setNombreParcelle(personneAffecte.getNombreParcelle());
        personneAffecte1.setCategorie(personneAffecte.getCategorie());
        personneAffecte1.setPrenom(personneAffecte.getPrenom());
        personneAffecte1.setNom(personneAffecte.getNom());
        personneAffecte1.setDateNaissance(personneAffecte.getDateNaissance());
        personneAffecte1.setLieuNaissance(personneAffecte.getLieuNaissance());
        personneAffecte1.setSexe(personneAffecte.getSexe());
        personneAffecte1.setPays(personneAffecte.getPays());
        personneAffecte1.setAge(personneAffecte.getAge());
        personneAffecte1.setNationalite(personneAffecte.getNationalite());
        personneAffecte1.setDepartement(personneAffecte.getDepartement());
        personneAffecte1.setTypeIdentification(personneAffecte.getTypeIdentification());
        personneAffecte1.setNumeroIdentification(personneAffecte.getNumeroIdentification());
        personneAffecte1.setNumeroTelephone(personneAffecte.getNumeroTelephone());
        personneAffecte1.setLocaliteResidence(personneAffecte.getLocaliteResidence());
        personneAffecte1.setRegion(personneAffecte.getRegion());
        personneAffecte1.setPrenomMere(personneAffecte.getPrenomMere());
        personneAffecte1.setPrenomPere(personneAffecte.getPrenomPere());
        personneAffecte1.setStatutPap(personneAffecte.getStatutPap());
        personneAffecte1.setStatutVulnerable(personneAffecte.getStatutVulnerable());
        personneAffecte1.setSituationMatrimoniale(personneAffecte.getSituationMatrimoniale());
        personneAffecte1.setPrenomExploitant(personneAffecte.getPrenomExploitant());
        personneAffecte1.setNomExploitant(personneAffecte.getNomExploitant());
        personneAffecte1.setSuperficieAffecte(personneAffecte.getSuperficieAffecte());
        personneAffecte1.setTypeCulture(personneAffecte.getTypeCulture());
        personneAffecte1.setTypeEquipement(personneAffecte.getTypeEquipement());
        personneAffecte1.setSuperficieCultive(personneAffecte.getSuperficieCultive());
        personneAffecte1.setSignaturePath(personneAffecte.getSignaturePath());
        personneAffecte1.setDescriptionBienAffecte(personneAffecte.getDescriptionBienAffecte());
            personneAffecte1.setProject(defaultProject);
        return convertToDto(personneAffecteRepository.save(personneAffecte1));
    }

    @Transactional
    @Override
    public PersonneAffecteDTO updatePersonneAffecte(Long id, PersonneAffecteDTO personneAffecteDetails) {

        Optional<PersonneAffecte> partieInteresse=personneAffecteRepository.findByNumeroIdentification(personneAffecteDetails.getNumeroIdentification());
        if(partieInteresse.isPresent()){
            throw new PersonneAffecteAlreadyExistsException("Ce numéro d'identification existe déjà.");
        }

        Optional<PersonneAffecte> personneAffecteOptional = personneAffecteRepository.findById(id);

        if (personneAffecteOptional.isPresent()) {
            PersonneAffecte personneAffecteToUpdate = personneAffecteOptional.get();

            Project defaultProject = projectRepository.findById((long) personneAffecteDetails.getProject_id())
                    .orElseThrow(() -> new RuntimeException("Project not found"));

            personneAffecteToUpdate.setIdPap(personneAffecteDetails.getIdPap());
            personneAffecteToUpdate.setIdParcelle(personneAffecteDetails.getIdParcelle());
            personneAffecteToUpdate.setNombreParcelle(personneAffecteDetails.getNombreParcelle());
            personneAffecteToUpdate.setCategorie(personneAffecteDetails.getCategorie());
            personneAffecteToUpdate.setPrenom(personneAffecteDetails.getPrenom());
            personneAffecteToUpdate.setNom(personneAffecteDetails.getNom());
            personneAffecteToUpdate.setDateNaissance(personneAffecteDetails.getDateNaissance());
            personneAffecteToUpdate.setLieuNaissance(personneAffecteDetails.getLieuNaissance());
            personneAffecteToUpdate.setSexe(personneAffecteDetails.getSexe());
            personneAffecteToUpdate.setPays(personneAffecteDetails.getPays());
            personneAffecteToUpdate.setAge(personneAffecteDetails.getAge());
            personneAffecteToUpdate.setNationalite(personneAffecteDetails.getNationalite());
            personneAffecteToUpdate.setDepartement(personneAffecteDetails.getDepartement());
            personneAffecteToUpdate.setTypeIdentification(personneAffecteDetails.getTypeIdentification());
            personneAffecteToUpdate.setNumeroIdentification(personneAffecteDetails.getNumeroIdentification());
            personneAffecteToUpdate.setNumeroTelephone(personneAffecteDetails.getNumeroTelephone());
            personneAffecteToUpdate.setLocaliteResidence(personneAffecteDetails.getLocaliteResidence());
            personneAffecteToUpdate.setRegion(personneAffecteDetails.getRegion());
            personneAffecteToUpdate.setPrenomMere(personneAffecteDetails.getPrenomMere());
            personneAffecteToUpdate.setPrenomPere(personneAffecteDetails.getPrenomPere());
            personneAffecteToUpdate.setStatutPap(personneAffecteDetails.getStatutPap());
            personneAffecteToUpdate.setStatutVulnerable(personneAffecteDetails.getStatutVulnerable());
            personneAffecteToUpdate.setSituationMatrimoniale(personneAffecteDetails.getSituationMatrimoniale());
            personneAffecteToUpdate.setPrenomExploitant(personneAffecteDetails.getPrenomExploitant());
            personneAffecteToUpdate.setNomExploitant(personneAffecteDetails.getNomExploitant());
            personneAffecteToUpdate.setSuperficieAffecte(personneAffecteDetails.getSuperficieAffecte());
            personneAffecteToUpdate.setTypeCulture(personneAffecteDetails.getTypeCulture());
            personneAffecteToUpdate.setTypeEquipement(personneAffecteDetails.getTypeEquipement());
            personneAffecteToUpdate.setSignaturePath(personneAffecteDetails.getSignaturePath());
            personneAffecteToUpdate.setSuperficieCultive(personneAffecteDetails.getSuperficieCultive());
            personneAffecteToUpdate.setDescriptionBienAffecte(personneAffecteDetails.getDescriptionBienAffecte());
            personneAffecteToUpdate.setProject(defaultProject);
            PersonneAffecte savedPersonneAffecte = personneAffecteRepository.save(personneAffecteToUpdate);
            return convertToDto(savedPersonneAffecte);
        } else {
            throw new RuntimeException("PersonneAffecte not found with id " + id);
        }
    }


    @Override
    public void deletePersonneAffecte(Long id) {
        personneAffecteRepository.deleteById(id);
    }


    /*
    public Page<PersonneAffecte> getPersonneAffectes(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return personneAffecteRepository.findAll(pageable);
    }
    */


    public List<PersonneAffecteDTO> savePersonneAffectes(List<PersonneAffecteDTO> personneAffecteDTOs) {
        List<PersonneAffecte> personneAffectes = new ArrayList<>();

        for (PersonneAffecteDTO personneAffecteDTO : personneAffecteDTOs) {
            // Récupérer le projet lié
            Project defaultProject = projectRepository.findById((long) personneAffecteDTO.getProject_id())
                    .orElseThrow(() -> new RuntimeException("Project not found"));
            PersonneAffecte personneAffecte = new PersonneAffecte();
            personneAffecte.setIdPap(personneAffecteDTO.getIdPap());
            personneAffecte.setIdParcelle(personneAffecteDTO.getIdParcelle());
            personneAffecte.setNombreParcelle(personneAffecteDTO.getNombreParcelle());
            personneAffecte.setCategorie(personneAffecteDTO.getCategorie());
            personneAffecte.setPrenom(personneAffecteDTO.getPrenom());
            personneAffecte.setNom(personneAffecteDTO.getNom());
            personneAffecte.setDateNaissance(personneAffecteDTO.getDateNaissance());
            personneAffecte.setLieuNaissance(personneAffecteDTO.getLieuNaissance());
            personneAffecte.setSexe(personneAffecteDTO.getSexe());
            personneAffecte.setPays(personneAffecteDTO.getPays());
            personneAffecte.setAge(personneAffecteDTO.getAge());
            personneAffecte.setNationalite(personneAffecteDTO.getNationalite());
            personneAffecte.setDepartement(personneAffecteDTO.getDepartement());
            personneAffecte.setTypeIdentification(personneAffecteDTO.getTypeIdentification());
            personneAffecte.setNumeroIdentification(personneAffecteDTO.getNumeroIdentification());
            personneAffecte.setNumeroTelephone(personneAffecteDTO.getNumeroTelephone());
            personneAffecte.setLocaliteResidence(personneAffecteDTO.getLocaliteResidence());
            personneAffecte.setRegion(personneAffecteDTO.getRegion());
            personneAffecte.setPrenomMere(personneAffecteDTO.getPrenomMere());
            personneAffecte.setPrenomPere(personneAffecteDTO.getPrenomPere());
            personneAffecte.setStatutPap(personneAffecteDTO.getStatutPap());
            personneAffecte.setStatutVulnerable(personneAffecteDTO.getStatutVulnerable());
            personneAffecte.setSituationMatrimoniale(personneAffecteDTO.getSituationMatrimoniale());
            personneAffecte.setPrenomExploitant(personneAffecteDTO.getPrenomExploitant());
            personneAffecte.setNomExploitant(personneAffecteDTO.getNomExploitant());
            personneAffecte.setSuperficieAffecte(personneAffecteDTO.getSuperficieAffecte());
            personneAffecte.setTypeCulture(personneAffecteDTO.getTypeCulture());
            personneAffecte.setTypeEquipement(personneAffecteDTO.getTypeEquipement());
            personneAffecte.setSuperficieCultive(personneAffecteDTO.getSuperficieCultive());
            personneAffecte.setDescriptionBienAffecte(personneAffecteDTO.getDescriptionBienAffecte());
            personneAffecte.setSignaturePath(personneAffecteDTO.getSignaturePath());
            personneAffecte.setProject(defaultProject);

            personneAffectes.add(personneAffecte);
        }

        List<PersonneAffecte> savedPersonneAffectes = personneAffecteRepository.saveAll(personneAffectes);
        return savedPersonneAffectes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    public Page<PersonneAffecteDTO> getPersonneAffectes(int offset, int max) {
        Page<PersonneAffecte> personneAffectePage = personneAffecteRepository.findAll(PageRequest.of(offset, max));
        return personneAffectePage.map(this::convertToDto);
    }

    private PersonneAffecteDTO convertToDto(PersonneAffecte personneAffecte) {
        PersonneAffecteDTO dto = new PersonneAffecteDTO();
        dto.setId(personneAffecte.getId());
        dto.setIdPap(personneAffecte.getIdPap());
        dto.setIdParcelle(personneAffecte.getIdParcelle());
        dto.setNombreParcelle(personneAffecte.getNombreParcelle());
        dto.setCategorie(personneAffecte.getCategorie());
        dto.setPrenom(personneAffecte.getPrenom());
        dto.setNom(personneAffecte.getNom());
        dto.setDateNaissance(personneAffecte.getDateNaissance());
        dto.setLieuNaissance(personneAffecte.getLieuNaissance());
        dto.setSexe(personneAffecte.getSexe());
        dto.setPays(personneAffecte.getPays());
        dto.setAge(personneAffecte.getAge());
        dto.setNationalite(personneAffecte.getNationalite());
        dto.setDepartement(personneAffecte.getDepartement());
        dto.setTypeIdentification(personneAffecte.getTypeIdentification());
        dto.setNumeroIdentification(personneAffecte.getNumeroIdentification());
        dto.setNumeroTelephone(personneAffecte.getNumeroTelephone());
        dto.setLocaliteResidence(personneAffecte.getLocaliteResidence());
        dto.setRegion(personneAffecte.getRegion());
        dto.setPrenomMere(personneAffecte.getPrenomMere());
        dto.setPrenomPere(personneAffecte.getPrenomPere());
        dto.setStatutPap(personneAffecte.getStatutPap());
        dto.setStatutVulnerable(personneAffecte.getStatutVulnerable());
        dto.setSituationMatrimoniale(personneAffecte.getSituationMatrimoniale());
        dto.setPrenomExploitant(personneAffecte.getPrenomExploitant());
        dto.setNomExploitant(personneAffecte.getNomExploitant());
        dto.setSuperficieAffecte(personneAffecte.getSuperficieAffecte());
        dto.setTypeCulture(personneAffecte.getTypeCulture());
        dto.setTypeEquipement(personneAffecte.getTypeEquipement());
        dto.setSignaturePath(personneAffecte.getSignaturePath());
        dto.setImagePath(personneAffecte.getImagePath());
        dto.setSuperficieCultive(personneAffecte.getSuperficieCultive());
        dto.setDescriptionBienAffecte(personneAffecte.getDescriptionBienAffecte());
        dto.setProject_id(personneAffecte.getProject().getId()); // ID du projet
        dto.setCodePap(personneAffecte.getCodePap());
        return dto;
    }


    public PersonneAffecteDTO addImageToPap(Long id, String url) {
        Optional<PersonneAffecte> personneAffecteOptional = personneAffecteRepository.findById(id);
        if (personneAffecteOptional.isPresent()) {
            PersonneAffecte personneAffecteToUpdate = personneAffecteOptional.get();
            personneAffecteToUpdate.setImagePath(url);
            PersonneAffecte savedPersonneAffecte = personneAffecteRepository.save(personneAffecteToUpdate);
            return convertToDto(savedPersonneAffecte);
        } else {
            throw new RuntimeException("PersonneAffecte not found with id " + id);
        }
    }


    public PersonneAffecteDTO addSignatureToPap(Long id, String url) {
        Optional<PersonneAffecte> personneAffecteOptional = personneAffecteRepository.findById(id);
        if (personneAffecteOptional.isPresent()) {
            PersonneAffecte personneAffecteToUpdate = personneAffecteOptional.get();
            personneAffecteToUpdate.setSignaturePath(url);
            PersonneAffecte savedPersonneAffecte = personneAffecteRepository.save(personneAffecteToUpdate);
            return convertToDto(savedPersonneAffecte);
        } else {
            throw new RuntimeException("PersonneAffecte not found with id " + id);
        }
    }

    public PersonneAffecteDTO dedommagerPap(Long id, String url) {
        Optional<PersonneAffecte> personneAffecteOptional = personneAffecteRepository.findById(id);
        if (personneAffecteOptional.isPresent()) {
            PersonneAffecte personneAffecteToUpdate = personneAffecteOptional.get();
            personneAffecteToUpdate.setStatutPap(url);
            PersonneAffecte savedPersonneAffecte = personneAffecteRepository.save(personneAffecteToUpdate);
            return convertToDto(savedPersonneAffecte);
        } else {
            throw new RuntimeException("PersonneAffecte not found with id " + id);
        }
    }
}