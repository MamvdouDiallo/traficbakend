package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.PlainteDto;
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

import java.util.ArrayList;
import java.util.List;
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
        plainte.setProjet(projet);
        plainte.setNumeroDossier(plainteRequest.getNumeroDossier());
        plainte.setLieuEnregistrement(plainteRequest.getLieuEnregistrement());
        plainte.setDateEnregistrement(plainteRequest.getDateEnregistrement());
        plainte.setIsRecensed(plainteRequest.getIsRecensed());
        plainte.setLieuNaissance(plainteRequest.getLieuNaissance());
        plainte.setNom(plainteRequest.getNom());
        plainte.setPrenom(plainteRequest.getPrenom());
        plainte.setNumeroIdentification(plainteRequest.getNumeroIdentification());
        plainte.setPlaceOfBirth(plainteRequest.getPlaceOfBirth());
        plainte.setRecommandation(plainteRequest.getRecommandation());
        plainte.setSituationMatrimoniale(plainteRequest.getSituationMatrimoniale());
        plainte.setTypeIdentification(plainteRequest.getTypeIdentification());
        plainte.setCodePap(plainteRequest.getCodePap());
        plainte.setVulnerabilite(plainteRequest.getVulnerabilite());
        plainte.setEtat(plainteRequest.getEtat());
        plainte.setDocumentUrls(plainteRequest.getDocumentUrls());
        plainte = plainteRepository.save(plainte);

        return convertEntityToDto(plainte);
    }

    @Override
    public Page<PlainteDto> getAllPlaintes(Pageable pageable) {
        return plainteRepository.findAll(pageable)
                .map(this::convertEntityToDto);  // Utilisation de map() sur Page<Plainte>
    }


    @Override
    public PlainteDto getPlainteById(Long id) {
        Plainte plainte = plainteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plainte non trouvée"));
        return convertEntityToDto(plainte);
    }

    @Override
    public PlainteDto updatePlainte(Long id, PlainteRequest plainteRequest) {
        Plainte plainte = plainteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plainte non trouvée"));
        Project projet = projetRepository.findById(plainteRequest.getProjectId())
                .orElseThrow(() -> new RuntimeException("Projet non trouvé"));

        plainte.setProjet(projet);
        plainte.setNumeroDossier(plainteRequest.getNumeroDossier());
        plainte.setLieuEnregistrement(plainteRequest.getLieuEnregistrement());
        plainte.setDateEnregistrement(plainteRequest.getDateEnregistrement());
        plainte.setIsRecensed(plainteRequest.getIsRecensed());
        plainte.setLieuNaissance(plainteRequest.getLieuNaissance());
        plainte.setNom(plainteRequest.getNom());
        plainte.setPrenom(plainteRequest.getPrenom());
        plainte.setNumeroIdentification(plainteRequest.getNumeroIdentification());
        plainte.setPlaceOfBirth(plainteRequest.getPlaceOfBirth());
        plainte.setRecommandation(plainteRequest.getRecommandation());
        plainte.setSituationMatrimoniale(plainteRequest.getSituationMatrimoniale());
        plainte.setTypeIdentification(plainteRequest.getTypeIdentification());
        plainte.setVulnerabilite(plainteRequest.getVulnerabilite());
        plainte.setCodePap(plainteRequest.getCodePap());
        plainte.setDocumentUrls(plainteRequest.getDocumentUrls());
        plainte.setEtat(plainteRequest.getEtat());
        plainte = plainteRepository.save(plainte);

        return convertEntityToDto(plainte);
    }

    @Override
    public void deletePlainte(Long id) {
        plainteRepository.deleteById(id);
    }

    private PlainteDto convertEntityToDto(Plainte plainte) {
        PlainteDto plainteDto = new PlainteDto();
        plainteDto.setId(plainte.getId());
        plainteDto.setNumeroDossier(plainte.getNumeroDossier());
        plainteDto.setLieuEnregistrement(plainte.getLieuEnregistrement());
        plainteDto.setDateEnregistrement(plainte.getDateEnregistrement());
        plainteDto.setIsRecensed(plainte.getIsRecensed());
        plainteDto.setLieuNaissance(plainte.getLieuNaissance());
        plainteDto.setNom(plainte.getNom());
        plainteDto.setPrenom(plainte.getPrenom());
        plainteDto.setNumeroIdentification(plainte.getNumeroIdentification());
        plainteDto.setPlaceOfBirth(plainte.getPlaceOfBirth());
        plainteDto.setRecommandation(plainte.getRecommandation());
        plainteDto.setSituationMatrimoniale(plainte.getSituationMatrimoniale());
        plainteDto.setTypeIdentification(plainte.getTypeIdentification());
        plainteDto.setVulnerabilite(plainte.getVulnerabilite());
        plainteDto.setProjectId((long) plainte.getProjet().getId());
        plainteDto.setCodePap(plainte.getCodePap());
        plainteDto.setEtat(plainte.getEtat());
        plainteDto.setDocumentUrls(plainte.getDocumentUrls());
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
}