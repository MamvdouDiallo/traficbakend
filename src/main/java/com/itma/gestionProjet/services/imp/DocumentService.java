package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.DocumentDTO;
import com.itma.gestionProjet.requests.DocumentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DocumentService {
    DocumentDTO createDocument(DocumentRequest documentRequest);
    DocumentDTO updateDocument(Long id, DocumentRequest documentRequest);
    void deleteDocument(Long id);
    DocumentDTO getDocumentById(Long id);
    Page<DocumentDTO> getAllDocuments(Pageable pageable);

    Page<DocumentDTO> getDocumentsByProjectId(Long projectId, Pageable pageable);
}
