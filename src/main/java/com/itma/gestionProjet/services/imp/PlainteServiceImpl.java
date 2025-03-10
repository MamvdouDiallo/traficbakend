package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.EntenteCompensationPapDto;
import com.itma.gestionProjet.dtos.PlainteDto;
import com.itma.gestionProjet.dtos.PlainteInvalidDto;
import com.itma.gestionProjet.entities.EntenteCompensationPap;
import com.itma.gestionProjet.entities.Plainte;
import com.itma.gestionProjet.entities.Project;
import com.itma.gestionProjet.repositories.PlainteRepository;
import com.itma.gestionProjet.repositories.ProjectRepository;
import com.itma.gestionProjet.requests.PlainteRequest;
import com.itma.gestionProjet.services.PlainteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlainteServiceImpl implements PlainteService {

    @Autowired
    private PlainteRepository plainteRepository;

    @Autowired
    private ProjectRepository projetRepository;

    @Override
    public PlainteDto createPlainte(PlainteRequest plainteRequest) {
        Plainte plainte = new Plainte();

        Project projet = projetRepository.findById(plainteRequest.getProjectId())
                .orElseThrow(() -> new RuntimeException("Projet non trouvé"));

        plainte.setProject(projet);
        plainte.setNumeroDossier(plainteRequest.getNumeroDossier());
        plainte.setLibelleProjet(plainteRequest.getLibelleProjet());
        plainte.setLieuEnregistrement(plainteRequest.getLieuEnregistrement());
        plainte.setDateEnregistrement(plainteRequest.getDateEnregistrement());
        plainte.setIsRecensed(plainteRequest.getIsRecensed());
        plainte.setNom(plainteRequest.getNom());
        plainte.setPrenom(plainteRequest.getPrenom());
        plainte.setNumeroIdentification(plainteRequest.getNumeroIdentification());
        plainte.setRecommandation(plainteRequest.getRecommandation());
        plainte.setSituationMatrimoniale(plainteRequest.getSituationMatrimoniale());
        plainte.setTypeIdentification(plainteRequest.getTypeIdentification());
        plainte.setCodePap(plainteRequest.getCodePap());
        plainte.setVulnerabilite(plainteRequest.getVulnerabilite());
        plainte.setEtat(plainteRequest.getEtat());
        plainte.setDocumentUrls(plainteRequest.getDocumentUrls());
        plainte.setDescriptionObjet(plainteRequest.getDescriptionObjet());

        plainte.setIsSignedFileRecensement(plainteRequest.getIsSignedFileRecensement());
        plainte.setDateRecensement(plainteRequest.getDateRecensement());
        plainte.setNatureBienAffecte(plainteRequest.getNatureBienAffecte());
        plainte.setEmplacementBienAffecte(plainteRequest.getEmplacementBienAffecte());
        plainte.setContact(plainteRequest.getContact());
        plainte.setEmail(plainteRequest.getEmail());
        plainte.setHasDocument(plainteRequest.getHasDocument());
        plainte.setUrlSignaturePap(plainteRequest.getUrlSignaturePap());
        plainte.setUrlSignatureResponsable(plainteRequest.getUrlSignatureResponsable());

        plainte = plainteRepository.save(plainte);

        return convertEntityToDto(plainte);
    }


    @Override
    public Page<PlainteDto> getAllPlaintes(Pageable pageable) {
        return plainteRepository.findAll(pageable)
                .map(this::convertEntityToDto);
    }
    @Override
    public Page<PlainteDto> getPlaintesByProjectId(Long projectId, Pageable pageable) {
       return  plainteRepository.findByProjectId(projectId, pageable)
        .map(this::convertEntityToDto);
    }



    @Override
    public PlainteDto getPlainteById(Long id) {
        Plainte plainte = plainteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plainte non trouvée"));
        return convertEntityToDto(plainte);
    }

    @Override
    public PlainteDto updatePlainte(Long id, PlainteRequest plainteRequest) {
        // Recherche de la plainte par son ID
        Plainte plainte = plainteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plainte non trouvée"));

        Project projet = projetRepository.findById(plainteRequest.getProjectId())
                .orElseThrow(() -> new RuntimeException("Projet non trouvé"));

        plainte.setProject(projet);
        plainte.setNumeroDossier(plainteRequest.getNumeroDossier());
        plainte.setLieuEnregistrement(plainteRequest.getLieuEnregistrement());
        plainte.setDateEnregistrement(plainteRequest.getDateEnregistrement());
        plainte.setIsRecensed(plainteRequest.getIsRecensed());
        plainte.setNom(plainteRequest.getNom());
        plainte.setPrenom(plainteRequest.getPrenom());
        plainte.setNumeroIdentification(plainteRequest.getNumeroIdentification());
        plainte.setRecommandation(plainteRequest.getRecommandation());
        plainte.setSituationMatrimoniale(plainteRequest.getSituationMatrimoniale());
        plainte.setTypeIdentification(plainteRequest.getTypeIdentification());
        plainte.setCodePap(plainteRequest.getCodePap());
        plainte.setVulnerabilite(plainteRequest.getVulnerabilite());
        plainte.setEtat(plainteRequest.getEtat());
        plainte.setDocumentUrls(plainteRequest.getDocumentUrls());
        plainte.setLibelleProjet(plainteRequest.getLibelleProjet());
        plainte.setDescriptionObjet(plainteRequest.getDescriptionObjet());

        plainte.setIsSignedFileRecensement(plainteRequest.getIsSignedFileRecensement());
        plainte.setDateRecensement(plainteRequest.getDateRecensement());
        plainte.setNatureBienAffecte(plainteRequest.getNatureBienAffecte());
        plainte.setEmplacementBienAffecte(plainteRequest.getEmplacementBienAffecte());
        plainte.setContact(plainteRequest.getContact());
        plainte.setEmail(plainteRequest.getEmail());
        plainte.setHasDocument(plainteRequest.getHasDocument());
        plainte.setUrlSignaturePap(plainteRequest.getUrlSignaturePap());
        plainte.setUrlSignatureResponsable(plainteRequest.getUrlSignatureResponsable());

        plainte = plainteRepository.save(plainte);

        return convertEntityToDto(plainte);
    }


    @Override
    public void deletePlainte(Long id) {
        plainteRepository.deleteById(id);
    }

    @Override
    /*
    public List<PlainteDto> createPlaintes(List<PlainteRequest> plainteRequests) {
        List<PlainteDto> plaintesDtos = new ArrayList<>();

        for (PlainteRequest plainteRequest : plainteRequests) {
            // Utilisez la méthode existante pour chaque plainteRequest
            PlainteDto plainteDto = createPlainte(plainteRequest);
            plaintesDtos.add(plainteDto);
        }

        return plaintesDtos;
    }

     */
    public List<PlainteDto> createPlaintes(List<PlainteRequest> plainteRequests) {
        List<PlainteDto> plaintesValides = new ArrayList<>();
        List<PlainteInvalidDto> plaintesInvalides = new ArrayList<>();

        for (PlainteRequest plainteRequest : plainteRequests) {
            try {
                PlainteDto plainteDto = createPlainte(plainteRequest);
                plaintesValides.add(plainteDto);
            } catch (Exception e) {
                PlainteInvalidDto invalidDto = new PlainteInvalidDto();
                invalidDto.setPlainteRequest(plainteRequest);
                invalidDto.setErrorMessage(e.getMessage());
                plaintesInvalides.add(invalidDto);
            }
        }

        List<PlainteDto> allPlaintes = new ArrayList<>();
        allPlaintes.addAll(plaintesValides);
        return allPlaintes;
    }


    private PlainteDto convertEntityToDto(Plainte plainte) {
        PlainteDto plainteDto = new PlainteDto();

        plainteDto.setId(plainte.getId());
        plainteDto.setNumeroDossier(plainte.getNumeroDossier());
        plainteDto.setLieuEnregistrement(plainte.getLieuEnregistrement());
        plainteDto.setDateEnregistrement(plainte.getDateEnregistrement());
        plainteDto.setIsRecensed(plainte.getIsRecensed());
        plainteDto.setNom(plainte.getNom());
        plainteDto.setPrenom(plainte.getPrenom());
        plainteDto.setNumeroIdentification(plainte.getNumeroIdentification());
        plainteDto.setRecommandation(plainte.getRecommandation());
        plainteDto.setSituationMatrimoniale(plainte.getSituationMatrimoniale());
        plainteDto.setTypeIdentification(plainte.getTypeIdentification());
        plainteDto.setVulnerabilite(plainte.getVulnerabilite());
        plainteDto.setCodePap(plainte.getCodePap());
        plainteDto.setEtat(plainte.getEtat());
        plainteDto.setDescriptionObjet(plainte.getDescriptionObjet());
        plainteDto.setLibelleProjet(plainte.getLibelleProjet());
        plainteDto.setDocumentUrls(plainte.getDocumentUrls());

        plainteDto.setIsSignedFileRecensement(plainte.getIsSignedFileRecensement());
        plainteDto.setDateRecensement(plainte.getDateRecensement());
        plainteDto.setNatureBienAffecte(plainte.getNatureBienAffecte());
        plainteDto.setEmplacementBienAffecte(plainte.getEmplacementBienAffecte());
        plainteDto.setContact(plainte.getContact());
        plainteDto.setEmail(plainte.getEmail());
        plainteDto.setHasDocument(plainte.getHasDocument());
        plainteDto.setUrlSignaturePap(plainte.getUrlSignaturePap());
        plainteDto.setUrlSignatureResponsable(plainte.getUrlSignatureResponsable());

        return plainteDto;
    }


    @Override
    public AApiResponse<PlainteDto> getPlainteByCodePap(String codePap, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Plainte> plaintesPage = plainteRepository.findByCodePap(codePap, pageRequest);
        List<PlainteDto> plainteDtos = plaintesPage.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

        AApiResponse<PlainteDto> response = new AApiResponse<>();

        if (!plaintesPage.isEmpty()) {
            response.setResponseCode(200);
            response.setData((plainteDtos));
            response.setMessage("Plaintes trouvées avec succès.");
            response.setLength((int) plaintesPage.getTotalElements());
        } else {
            response.setResponseCode(404);
            response.setData(new ArrayList<>());
            response.setMessage("Aucune plainte trouvée avec le codePap fourni.");
            response.setLength(0);
        }

        return response;
    }


    @Override
    public AApiResponse<PlainteDto> searchGlobalPlaintes(String searchTerm, Optional<Long> projectId, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Plainte> pageResult = plainteRepository.searchGlobal(searchTerm, projectId, pageable);
            List<PlainteDto> data = pageResult.getContent().stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
            AApiResponse<PlainteDto> response = new AApiResponse<>();
            response.setResponseCode(200);
            response.setData(data);
            response.setOffset(page);
            response.setLength(pageResult.getTotalElements()); // Nombre total d'éléments
            response.setMax(size);
            response.setMessage("Successfully retrieved data.");
            return response;
        } catch (Exception e) {
            AApiResponse<PlainteDto> errorResponse = new AApiResponse<>();
            errorResponse.setResponseCode(500);
            errorResponse.setMessage("Error retrieving data: " + e.getMessage());
            return errorResponse;
        }
    }

}