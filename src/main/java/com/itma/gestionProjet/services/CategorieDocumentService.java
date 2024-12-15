package com.itma.gestionProjet.services;

import com.itma.gestionProjet.dtos.CategorieDocumentDTO;
import com.itma.gestionProjet.requests.CategorieDocumentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CategorieDocumentService {
    CategorieDocumentDTO createCategorieDocument(CategorieDocumentRequest categorieDocumentRequest);
    CategorieDocumentDTO updateCategorieDocument(Long id, CategorieDocumentRequest categorieDocumentRequest);
    void deleteCategorieDocument(Long id);
    CategorieDocumentDTO getCategorieDocumentById(Long id);
    Page<CategorieDocumentDTO> getAllCategorieDocuments(Pageable pageable);
}
