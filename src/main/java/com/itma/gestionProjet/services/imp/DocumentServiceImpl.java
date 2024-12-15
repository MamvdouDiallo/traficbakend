package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.DocumentDTO;
import com.itma.gestionProjet.entities.CategorieDocument;
import com.itma.gestionProjet.entities.Document;
import com.itma.gestionProjet.entities.Project;
import com.itma.gestionProjet.repositories.CategorieDocumentRepository;
import com.itma.gestionProjet.repositories.DocumentRepository;
import com.itma.gestionProjet.repositories.ProjectRepository;
import com.itma.gestionProjet.requests.DocumentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ProjectRepository projetRepository;

    @Autowired
    private CategorieDocumentRepository categorieDocumentRepository;

    @Override
    public DocumentDTO createDocument(DocumentRequest documentRequest) {
        Document document = new Document();
        document.setLibelle(documentRequest.getLibelle());
        document.setUrlDocument(documentRequest.getUrlDocument());

        Project projet = projetRepository.findById(documentRequest.getProjetId())
                .orElseThrow(() -> new RuntimeException("Projet not found"));
        document.setProjet(projet);

        CategorieDocument categorieDocument = categorieDocumentRepository.findById(documentRequest.getCategorieDocumentId())
                .orElseThrow(() -> new RuntimeException("CategorieDocument not found"));
        document.setCategorieDocument(categorieDocument);

        document = documentRepository.save(document);
        return mapToDTO(document);
    }

    @Override
    public DocumentDTO updateDocument(Long id, DocumentRequest documentRequest) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        document.setLibelle(documentRequest.getLibelle());
        document.setUrlDocument(documentRequest.getUrlDocument());

        Project projet = projetRepository.findById(documentRequest.getProjetId())
                .orElseThrow(() -> new RuntimeException("Projet not found"));
        document.setProjet(projet);

        CategorieDocument categorieDocument = categorieDocumentRepository.findById(documentRequest.getCategorieDocumentId())
                .orElseThrow(() -> new RuntimeException("CategorieDocument not found"));
        document.setCategorieDocument(categorieDocument);

        document = documentRepository.save(document);
        return mapToDTO(document);
    }

    @Override
    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }

    @Override
    public DocumentDTO getDocumentById(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        return mapToDTO(document);
    }

    @Override
    public Page<DocumentDTO> getAllDocuments(Pageable pageable) {
        return documentRepository.findAll(pageable).map(this::mapToDTO);
    }

    // Helper method to map Document entity to DocumentDTO
    private DocumentDTO mapToDTO(Document document) {
        return new DocumentDTO(document.getId(), document.getLibelle(), document.getUrlDocument(),
               document.getCategorieDocument());
    }
}
