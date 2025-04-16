package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.DocumentDTO;
import com.itma.gestionProjet.entities.CategorieDocument;
import com.itma.gestionProjet.entities.Document;
import com.itma.gestionProjet.entities.Project;
import com.itma.gestionProjet.repositories.CategorieDocumentRepository;
import com.itma.gestionProjet.repositories.DocumentRepository;
import com.itma.gestionProjet.repositories.ProjectRepository;
import com.itma.gestionProjet.requests.DocumentRequest;
import com.itma.gestionProjet.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ProjectRepository projetRepository;

    @Autowired
    private CategorieDocumentRepository categorieDocumentRepository;

    @Autowired
    private DocumentRepository dossierRepository;

    @Autowired
    private JwtUtil jwtUtil;


    /*
    @Override
    public DocumentDTO createDocument(DocumentRequest documentRequest) {
        Document document = new Document();
        document.setLibelle(documentRequest.getLibelle());
        document.setUrlDocument(documentRequest.getUrlDocument());

        Project projet = projetRepository.findById(documentRequest.getProjetId())
                .orElseThrow(() -> new RuntimeException("Projet not found"));
        document.setProject(projet);

        CategorieDocument categorieDocument = categorieDocumentRepository.findById(documentRequest.getCategorieDocumentId())
                .orElseThrow(() -> new RuntimeException("CategorieDocument not found"));
        document.setCategorieDocument(categorieDocument);

        document = documentRepository.save(document);
        return mapToDTO(document);
    }

     */
    public DocumentDTO createDocument(DocumentRequest documentRequest) {
        if (documentRequest.getProjectId() == null) {
            throw new IllegalArgumentException("L'ID du projet est requis");
        }
        Document document = new Document();
        document.setLibelle(documentRequest.getLibelle());
        document.setUrlDocument(documentRequest.getUrlDocument());

        Project projet = projetRepository.findById(documentRequest.getProjectId())
                .orElseThrow(() -> new RuntimeException("Projet non trouvé avec l'ID: " + documentRequest.getProjectId()));
        document.setProject(projet);
        if (documentRequest.getCategorieDocumentId() != null) {
            CategorieDocument categorieDocument = categorieDocumentRepository.findById(documentRequest.getCategorieDocumentId())
                    .orElseThrow(() -> new RuntimeException("Catégorie de document non trouvée avec l'ID: " + documentRequest.getCategorieDocumentId()));
            document.setCategorieDocument(categorieDocument);
        }
        try {
            document = documentRepository.save(document);
            return mapToDTO(document);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Erreur lors de la création du document: " + e.getRootCause().getMessage());
        }
    }

    @Override
    public DocumentDTO updateDocument(Long id, DocumentRequest documentRequest) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        document.setLibelle(documentRequest.getLibelle());
        document.setUrlDocument(documentRequest.getUrlDocument());

        Project projet = projetRepository.findById(documentRequest.getProjectId())
                .orElseThrow(() -> new RuntimeException("Projet not found"));
        document.setProject(projet);

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


    public Page<DocumentDTO> getDocumentsByProjectId(Long projectId, Pageable pageable) {
        return documentRepository.findByProjectId(projectId, pageable)
                .map(this::mapToDTO);
    }

    // Helper method to map Document entity to DocumentDTO
    private DocumentDTO mapToDTO(Document document) {
        return new DocumentDTO(document.getId(), document.getLibelle(), document.getUrlDocument(),
               document.getCategorieDocument());
    }

/*
    public List<Document> getDossiersByUser(String token) {
        List<String> roles = jwtUtil.extractRoles(token); // Récupère les rôles

        // Si l'utilisateur est Super Admin, il voit tout
        if (roles.contains("SUPER_ADMIN")) {
            return documentRepository.findAll();
        }

        // Sinon, on filtre selon ses projets
      //  List<Long> projetIds = jwtUtil.extractProjectIds(token);
        return documentRepository.findByProjetIdIn(projetIds);
    }

 */
}
