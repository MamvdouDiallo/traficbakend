package com.itma.gestionProjet.services;

import com.itma.gestionProjet.dtos.ArticleEntenteDto;

import java.util.List;

public interface ArticleEntenteService {
    ArticleEntenteDto createArticleEntente(ArticleEntenteDto articleEntenteDto);
    ArticleEntenteDto getArticleEntenteById(Long id);
    List<ArticleEntenteDto> getAllArticleEntentes();
    void deleteArticleEntente(Long id);
}
