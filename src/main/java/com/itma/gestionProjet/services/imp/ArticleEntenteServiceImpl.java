package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.ArticleEntenteDto;
import com.itma.gestionProjet.entities.ArticleEntente;
import com.itma.gestionProjet.repositories.ArticleEntenteRepository;
import com.itma.gestionProjet.services.ArticleEntenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleEntenteServiceImpl implements ArticleEntenteService {
    @Autowired
    private ArticleEntenteRepository articleEntenteRepository;

    @Override
    public ArticleEntenteDto createArticleEntente(ArticleEntenteDto articleEntenteDto) {
        ArticleEntente articleEntente = convertDtoToEntity(articleEntenteDto);
        articleEntente = articleEntenteRepository.save(articleEntente);
        return convertEntityToDto(articleEntente);
    }

    @Override
    public ArticleEntenteDto getArticleEntenteById(Long id) {
        return articleEntenteRepository.findById(id)
                .map(this::convertEntityToDto)
                .orElseThrow(() -> new RuntimeException("ArticleEntente not found"));
    }

    @Override
    public List<ArticleEntenteDto> getAllArticleEntentes() {
        return articleEntenteRepository.findAll().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteArticleEntente(Long id) {
        articleEntenteRepository.deleteById(id);
    }

    private ArticleEntenteDto convertEntityToDto(ArticleEntente articleEntente) {
        ArticleEntenteDto dto = new ArticleEntenteDto();
        dto.setId(articleEntente.getId());
        dto.setDescription(articleEntente.getDescription());
        dto.setEntenteCompensationId(articleEntente.getEntenteCompensation().getId());
        return dto;
    }
    private ArticleEntente convertDtoToEntity(ArticleEntenteDto dto) {
        ArticleEntente articleEntente = new ArticleEntente();
        articleEntente.setDescription(dto.getDescription());
        return articleEntente;
    }
}