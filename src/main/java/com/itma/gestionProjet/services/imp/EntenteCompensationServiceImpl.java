package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.ArticleEntenteDto;
import com.itma.gestionProjet.dtos.EntenteCompensationDto;
import com.itma.gestionProjet.entities.ArticleEntente;
import com.itma.gestionProjet.entities.EntenteCompensation;
import com.itma.gestionProjet.repositories.ArticleEntenteRepository;
import com.itma.gestionProjet.repositories.EntenteCompensationRepository;
import com.itma.gestionProjet.services.EntenteCompensationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EntenteCompensationServiceImpl implements EntenteCompensationService {

    @Autowired
    private ArticleEntenteRepository articleEntenteRepository;
    @Autowired
    private EntenteCompensationRepository ententeCompensationRepository;

    @Override
    public EntenteCompensationDto createEntenteCompensation(EntenteCompensationDto ententeCompensationDto) {
        // Convertir le DTO en entité
        EntenteCompensation ententeCompensation = convertDtoToEntity(ententeCompensationDto);

        // Sauvegarder les articles d'abord
        if (ententeCompensation.getArticles() != null) {
            List<ArticleEntente> savedArticles = new ArrayList<>();
            for (ArticleEntente article : ententeCompensation.getArticles()) {
                ArticleEntente savedArticle = articleEntenteRepository.save(article);
                savedArticles.add(savedArticle);
            }
            // Associer les articles sauvegardés à l'entente de compensation
            ententeCompensation.setArticles(savedArticles);
        }

        // Sauvegarder l'entente de compensation
        ententeCompensation = ententeCompensationRepository.save(ententeCompensation);

        // Établir la liaison entre les articles et l'entente de compensation après la sauvegarde
        for (ArticleEntente article : ententeCompensation.getArticles()) {
            article.setEntenteCompensation(ententeCompensation);
            articleEntenteRepository.save(article);  // Mise à jour avec la liaison
        }

        return convertEntityToDto(ententeCompensation);
    }

    @Override
    public EntenteCompensationDto getEntenteCompensationById(Long id) {
        return ententeCompensationRepository.findById(id)
                .map(this::convertEntityToDto)
                .orElseThrow(() -> new RuntimeException("EntenteCompensation not found"));
    }

    @Override
    public List<EntenteCompensationDto> getAllEntenteCompensations() {
        return ententeCompensationRepository.findAll().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }


    @Override
    public Page<EntenteCompensationDto> getAllEntenteCompensations(Pageable pageable) {
        return ententeCompensationRepository.findAll(pageable)
                .map(this::convertEntityToDto);
    }

    @Override
    public void deleteEntenteCompensation(Long id) {
        ententeCompensationRepository.deleteById(id);
    }

    private EntenteCompensationDto convertEntityToDto(EntenteCompensation ententeCompensation) {
        EntenteCompensationDto dto = new EntenteCompensationDto();
        dto.setId(ententeCompensation.getId());
        dto.setLibelleProjet(ententeCompensation.getLibelleProjet());
        dto.setCodePap(ententeCompensation.getCodePap());
        dto.setPrenom(ententeCompensation.getPrenom());
        dto.setNom(ententeCompensation.getNom());
        dto.setDateNaissance(ententeCompensation.getDateNaissance());
        dto.setLieuNaissance(ententeCompensation.getLieuNaissance());
        dto.setNaturePiece(ententeCompensation.getNaturePiece());
        dto.setNumeroPiece(ententeCompensation.getNumeroPiece());
        dto.setDateEnregistrement(ententeCompensation.getDateEnregistrement());
        dto.setLocaliteEnregistrement(ententeCompensation.getLocaliteEnregistrement());
        dto.setArticles(ententeCompensation.getArticles().stream()
                .map(this::convertArticleEntityToDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private EntenteCompensation convertDtoToEntity(EntenteCompensationDto dto) {
        EntenteCompensation ententeCompensation = new EntenteCompensation();
        ententeCompensation.setLibelleProjet(dto.getLibelleProjet());
        ententeCompensation.setCodePap(dto.getCodePap());
        ententeCompensation.setPrenom(dto.getPrenom());
        ententeCompensation.setNom(dto.getNom());
        ententeCompensation.setDateNaissance(dto.getDateNaissance());
        ententeCompensation.setLieuNaissance(dto.getLieuNaissance());
        ententeCompensation.setNaturePiece(dto.getNaturePiece());
        ententeCompensation.setNumeroPiece(dto.getNumeroPiece());
        ententeCompensation.setDateEnregistrement(dto.getDateEnregistrement());
        ententeCompensation.setLocaliteEnregistrement(dto.getLocaliteEnregistrement());
        ententeCompensation.setArticles(dto.getArticles().stream()
                .map(this::convertArticleDtoToEntity)
                .collect(Collectors.toList()));
        return ententeCompensation;
    }

    private ArticleEntenteDto convertArticleEntityToDto(ArticleEntente articleEntente) {
        ArticleEntenteDto dto = new ArticleEntenteDto();
        dto.setId(articleEntente.getId());
        dto.setTitre(articleEntente.getTitre());
        dto.setDescription(articleEntente.getDescription());
     dto.setEntenteCompensationId(articleEntente.getEntenteCompensation().getId());
        return dto;
    }

    private ArticleEntente convertArticleDtoToEntity(ArticleEntenteDto dto) {
        ArticleEntente articleEntente = new ArticleEntente();
        articleEntente.setTitre(dto.getTitre());
        articleEntente.setDescription(dto.getDescription());
        return articleEntente;
    }

    @Override
    public AApiResponse<EntenteCompensationDto> getEntenteCompensationByCodePap(String codePap) {
        Optional<List<EntenteCompensation>> ententeCompensations = ententeCompensationRepository.findByCodePap(codePap);
        List<EntenteCompensationDto> ententeCompensationDtos = new ArrayList<>();

        AApiResponse<EntenteCompensationDto> response = new AApiResponse<>();

        ententeCompensations.ifPresent(ententeList -> {
            for (EntenteCompensation ententeCompensation : ententeList) {
                EntenteCompensationDto ententeCompensationDto = convertEntityToDto(ententeCompensation);
                ententeCompensationDtos.add(ententeCompensationDto);
            }
        });

        if (!ententeCompensations.isEmpty()) {
            response.setResponseCode(200);
            response.setData(ententeCompensationDtos);  // Fix: Assign the list directly
            response.setMessage("Entente trouvées avec succès.");
            response.setLength(ententeCompensationDtos.size());
        } else {
            response.setResponseCode(404);
            response.setData(new ArrayList<>());  // An empty list if no data found
            response.setMessage("Aucune entente trouvée avec le codePap fourni.");
            response.setLength(0);
        }

        return response;
    }

}
