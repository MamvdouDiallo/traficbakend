package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.CategorieDocumentDTO;
import com.itma.gestionProjet.entities.CategorieDocument;
import com.itma.gestionProjet.repositories.CategorieDocumentRepository;
import com.itma.gestionProjet.requests.CategorieDocumentRequest;
import com.itma.gestionProjet.services.CategorieDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategorieDocumentServiceImpl implements CategorieDocumentService {

    @Autowired
    private CategorieDocumentRepository categorieDocumentRepository;

    @Override
    public CategorieDocumentDTO createCategorieDocument(CategorieDocumentRequest categorieDocumentRequest) {
        CategorieDocument categorieDocument = new CategorieDocument();
        categorieDocument.setLibelle(categorieDocumentRequest.getLibelle());

        categorieDocument = categorieDocumentRepository.save(categorieDocument);
        return mapToDTO(categorieDocument);
    }

    @Override
    public CategorieDocumentDTO updateCategorieDocument(Long id, CategorieDocumentRequest categorieDocumentRequest) {
        CategorieDocument categorieDocument = categorieDocumentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CategorieDocument not found"));

        categorieDocument.setLibelle(categorieDocumentRequest.getLibelle());
        categorieDocument = categorieDocumentRepository.save(categorieDocument);

        return mapToDTO(categorieDocument);
    }

    @Override
    public void deleteCategorieDocument(Long id) {
        categorieDocumentRepository.deleteById(id);
    }

    @Override
    public CategorieDocumentDTO getCategorieDocumentById(Long id) {
        CategorieDocument categorieDocument = categorieDocumentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CategorieDocument not found"));
        return mapToDTO(categorieDocument);
    }

    @Override
    public Page<CategorieDocumentDTO> getAllCategorieDocuments(Pageable pageable) {
        return categorieDocumentRepository.findAll(pageable).map(this::mapToDTO);
    }

    // Helper method to map CategorieDocument entity to CategorieDocumentDTO
    private CategorieDocumentDTO mapToDTO(CategorieDocument categorieDocument) {
        return new CategorieDocumentDTO(categorieDocument.getId(), categorieDocument.getLibelle());
    }
}
