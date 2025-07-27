package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.DatabasePapAgricoleRequestDTO;
import com.itma.gestionProjet.dtos.DatabasePapAgricoleResponseDTO;
import com.itma.gestionProjet.dtos.DatabasePapPlaceAffaireRequestDTO;
import com.itma.gestionProjet.dtos.DatabasePapPlaceAffaireResponseDTO;
import com.itma.gestionProjet.entities.DatabasePapAgricole;
import com.itma.gestionProjet.entities.DatabasePapPlaceAffaire;
import com.itma.gestionProjet.entities.EntenteCompensationPap;
import com.itma.gestionProjet.entities.Project;
import com.itma.gestionProjet.repositories.DatabasePapPlaceAffaireRepository;
import com.itma.gestionProjet.repositories.ProjectRepository;
import com.itma.gestionProjet.requests.EntenteCompensationPapRequest;
import com.itma.gestionProjet.services.DatabasePapPlaceAffaireService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DatabasePapPlaceAffaireServiceImpl implements DatabasePapPlaceAffaireService {

    @Autowired
    private DatabasePapPlaceAffaireRepository repository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void createDatabasePapPlaceAffaire(List<DatabasePapPlaceAffaireRequestDTO> requestDTOs) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.typeMap(DatabasePapPlaceAffaireRequestDTO.class, DatabasePapPlaceAffaire.class)
                .addMappings(mapper -> mapper.skip(DatabasePapPlaceAffaire::setId));

        List<DatabasePapPlaceAffaire> entities = requestDTOs.stream().map(dto -> {
            DatabasePapPlaceAffaire entity = modelMapper.map(dto, DatabasePapPlaceAffaire.class);
            if (entity.getType() == null || entity.getType().isEmpty()) {
                entity.setType("PAPPLACEAFFAIRE");
            }
            if (entity.getStatutPap() == null || entity.getStatutPap().isEmpty()) {
                entity.setType("recense");
            }
            if (dto.getProjectId() != null) {
                Project project = projectRepository.findById(dto.getProjectId())
                        .orElseThrow(() -> new RuntimeException("Project not found with ID: " + dto.getProjectId()));
                entity.setProject(project);
            }

            // Déterminer la vulnérabilité
            entity.setVulnerabilite(determinerVulnerabilite(entity));

            return entity;
        }).collect(Collectors.toList());
        repository.saveAll(entities);
    }


    private String determinerVulnerabilite(DatabasePapPlaceAffaire pap) {
        List<String> vulnerabilites = new ArrayList<>();

        // 1. Situation matrimoniale précaire
        if (pap.getSituationMatrimoniale() != null &&
                Arrays.asList("veuf", "veuve", "divorcé", "divorcée", "célibataire")
                        .contains(pap.getSituationMatrimoniale().toLowerCase())) {
            vulnerabilites.add("Situation matrimoniale précaire");
        }

        // 2. Ménage avec personne handicapée
        if (pap.getMembreFoyerHandicape() != null &&
                !pap.getMembreFoyerHandicape().isEmpty() &&
                !"non".equalsIgnoreCase(pap.getMembreFoyerHandicape())) {
            vulnerabilites.add("Ménage avec personne handicapée");
        }

        // 3. Mineur chef de ménage
//        if (pap.getDateNaissance() != null) {
//            int age = calculerAge(pap.getDateNaissance());
//            if (age < 18 && "chef de ménage".equalsIgnoreCase(pap.getRoleDansFoyer())) {
//                vulnerabilites.add("Mineur chef de ménage");
//            }
//
//            // 4. Personne âgée sans soutien
//            if (age >= 65 && (pap.getMembreFoyer() == null || pap.getMembreFoyer().isEmpty() || "seul".equalsIgnoreCase(pap.getMembreFoyer()))) {
//                vulnerabilites.add("Personne âgée sans soutien");
//            }
//        }

        if (pap.getDateNaissance() != null || pap.getAge() != null) {
            int age;

            // Si l'âge est directement disponible, on l'utilise
            if (pap.getAge() != null) {
                age = Math.toIntExact(pap.getAge());
            }
            // Sinon on le calcule à partir de la date de naissance
            else {
                age = calculerAge(pap.getDateNaissance());
            }

            // Vérifications des vulnérabilités
            if (age < 18 && "chef de ménage".equalsIgnoreCase(pap.getRoleDansFoyer())) {
                vulnerabilites.add("Mineur chef de ménage");
            }

            // 4. Personne âgée sans soutien
            if (age >= 65 && (pap.getMembreFoyer() == null || pap.getMembreFoyer().isEmpty() || "seul".equalsIgnoreCase(pap.getMembreFoyer()))) {
                vulnerabilites.add("Personne âgée sans soutien");
            }
        }

        // 5. Ménage avec plusieurs personnes à charge
        if (pap.getMembreFoyer() != null && !pap.getMembreFoyer().isEmpty()) {
            try {
                int nbDependants = Integer.parseInt(pap.getMembreFoyer());
                // Supposons qu'une personne travaille si elle a une activité principale
                boolean aActivite = pap.getActivitePrincipale() != null && !pap.getActivitePrincipale().isEmpty();
                if (nbDependants >= 4 && aActivite) {
                    vulnerabilites.add("Ménage nombreux");
                }
            } catch (NumberFormatException e) {
                // Gérer le cas où membreFoyer n'est pas un nombre
            }
        }

        // 6. Analphabète
        if (pap.getNiveauEtude() != null &&
                (pap.getNiveauEtude().toLowerCase().contains("ne sait pas lire") ||
                        "analphabète".equalsIgnoreCase(pap.getNiveauEtude()))) {
            vulnerabilites.add("Analphabétisme");
        }

        return vulnerabilites.isEmpty() ? "Non vulnérable" : String.join(", ", vulnerabilites);
    }

    private int calculerAge(LocalDate dateNaissance) {
        return Period.between(dateNaissance, LocalDate.now()).getYears();
    }
    @Override
    public Map<String, Object> getVulnerabilityStats(Long projectId) {
        var pageRequest = PageRequest.of(0, 10000000);
        Page<DatabasePapPlaceAffaire> pagedPaps = repository.findByProjectId(projectId, pageRequest);
        List<DatabasePapPlaceAffaire> projectPaps = pagedPaps.getContent();

        // 1. Initialisation des structures
        // Vulnérabilités globales
        Map<String, Long> globalVulnerabilities = initVulnerabilityMap();

        // Sexes globaux
        Map<String, Long> globalGenders = initGenderMap(projectPaps.size());

        // Vulnérabilités par sexe (analyse croisée)
        Map<String, Map<String, Long>> vulnerabilitiesByGender = initCrossAnalysisMap();

        // Terminologie des sexes
//        Set<String> maleTerms = Set.of("m", "masculin", "homme", "male", "garçon", "h", "mâle");
//        Set<String> femaleTerms = Set.of("f", "féminin", "femme", "femelle", "female", "fille");
        Set<String> maleTerms = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        maleTerms.addAll(Arrays.asList("m", "masculin", "homme", "male", "garçon", "h", "mâle"));

        Set<String> femaleTerms = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        femaleTerms.addAll(Arrays.asList("f", "féminin", "femme", "femelle", "female", "fille","feminin"));

        // 2. Traitement des données
        for (DatabasePapPlaceAffaire pap : projectPaps) {
            String sexe = pap.getSexe();
            String normalizedSexe = sexe != null ? sexe.trim().toLowerCase() : "";

            // Détection du sexe
            String genderKey = getGenderKey(normalizedSexe, maleTerms, femaleTerms);
            globalGenders.put(genderKey, globalGenders.get(genderKey) + 1);

            // Traitement vulnérabilité
            String vuln = pap.getVulnerabilite();
            boolean isNonVulnerable = vuln == null || vuln.equals("Non vulnérable");

            if (isNonVulnerable) {
                globalVulnerabilities.put("Non vulnérable", globalVulnerabilities.get("Non vulnérable") + 1);
                vulnerabilitiesByGender.get("Non vulnérable").put(genderKey,
                        vulnerabilitiesByGender.get("Non vulnérable").get(genderKey) + 1);
                continue;
            }

            // Analyse par catégorie
            for (String category : globalVulnerabilities.keySet()) {
                if (vuln.contains(category)) {
                    globalVulnerabilities.put(category, globalVulnerabilities.get(category) + 1);
                    vulnerabilitiesByGender.get(category).put(genderKey,
                            vulnerabilitiesByGender.get(category).get(genderKey) + 1);
                }
            }
        }

        // 3. Construction du résultat
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("Vulnerabilites_globales", globalVulnerabilities);
        result.put("Sexes_globaux", globalGenders);
        result.put("Vulnerabilites_par_sexe", vulnerabilitiesByGender);

        return result;
    }

    // Méthodes utilitaires
    private Map<String, Long> initVulnerabilityMap() {
        Map<String, Long> map = new LinkedHashMap<>();
        map.put("Situation matrimoniale précaire", 0L);
        map.put("Ménage avec personne handicapée", 0L);
        map.put("Mineur chef de ménage", 0L);
        map.put("Personne âgée sans soutien", 0L);
        map.put("Ménage nombreux", 0L);
        map.put("Analphabétisme", 0L);
        map.put("Non vulnérable", 0L);
        return map;
    }

    private Map<String, Long> initGenderMap(long total) {
        Map<String, Long> map = new LinkedHashMap<>();
        map.put("Total", total);
        map.put("Hommes", 0L);
        map.put("Femmes", 0L);
        map.put("Autre", 0L);
        return map;
    }

    private Map<String, Map<String, Long>> initCrossAnalysisMap() {
        Map<String, Map<String, Long>> map = new LinkedHashMap<>();
        for (String category : initVulnerabilityMap().keySet()) {
            Map<String, Long> genderMap = new LinkedHashMap<>();
            genderMap.put("Hommes", 0L);
            genderMap.put("Femmes", 0L);
            genderMap.put("Autre", 0L);
            map.put(category, genderMap);
        }
        return map;
    }

    private String getGenderKey(String normalizedSexe, Set<String> maleTerms, Set<String> femaleTerms) {
        if (femaleTerms.contains(normalizedSexe)) return "Femmes";
        if (maleTerms.contains(normalizedSexe)) return "Hommes";
        return "Autre";
    }

//    @Override
//    public Map<String, Map<String, Long>> getVulnerabilityStats(Long projectId) {
//        var pageRequest = PageRequest.of(0, 10000000);
//        Page<DatabasePapPlaceAffaire> pagedPaps = repository.findByProjectId(projectId, pageRequest);
//        List<DatabasePapPlaceAffaire> projectPaps = pagedPaps.getContent();
//
//        // Structure finale qui contiendra les deux catégories
//        Map<String, Map<String, Long>> result = new LinkedHashMap<>();
//
//        // 1. Statistiques de vulnérabilité
//        Map<String, Long> vulnerabilityStats = new LinkedHashMap<>();
//        vulnerabilityStats.put("Situation matrimoniale précaire", 0L);
//        vulnerabilityStats.put("Ménage avec personne handicapée", 0L);
//        vulnerabilityStats.put("Mineur chef de ménage", 0L);
//        vulnerabilityStats.put("Personne âgée sans soutien", 0L);
//        vulnerabilityStats.put("Ménage nombreux", 0L);
//        vulnerabilityStats.put("Analphabétisme", 0L);
//        vulnerabilityStats.put("Non vulnérable", 0L);
//
//        // 2. Statistiques par sexe
//        Map<String, Long> genderStats = new LinkedHashMap<>();
//        genderStats.put("Total", (long) projectPaps.size());
//        genderStats.put("Hommes", 0L);
//        genderStats.put("Femmes", 0L);
//        genderStats.put("Autre", 0L);
//
//        // Définition des termes reconnus pour chaque genre
//        Set<String> maleTerms = Set.of("m", "masculin", "homme", "male", "garçon", "h", "mâle");
//        Set<String> femaleTerms = Set.of("f", "féminin", "femme", "femelle", "female", "fille");
//
//        for (DatabasePapPlaceAffaire pap : projectPaps) {
//            // Traitement du sexe
//            String sexe = pap.getSexe();
//            if (sexe != null) {
//                String normalizedSexe = sexe.trim().toLowerCase();
//                if (femaleTerms.contains(normalizedSexe)) {
//                    genderStats.put("Femmes", genderStats.get("Femmes") + 1);
//                } else if (maleTerms.contains(normalizedSexe)) {
//                    genderStats.put("Hommes", genderStats.get("Hommes") + 1);
//                } else {
//                    genderStats.put("Autre", genderStats.get("Autre") + 1);
//                }
//            } else {
//                genderStats.put("Autre", genderStats.get("Autre") + 1);
//            }
//
//            // Traitement de la vulnérabilité
//            String vuln = pap.getVulnerabilite();
//            if (vuln == null || vuln.equals("Non vulnérable")) {
//                vulnerabilityStats.put("Non vulnérable", vulnerabilityStats.get("Non vulnérable") + 1);
//                continue;
//            }
//
//            for (String type : vulnerabilityStats.keySet()) {
//                if (vuln.contains(type)) {
//                    vulnerabilityStats.put(type, vulnerabilityStats.get(type) + 1);
//                }
//            }
//        }
//
//        // Ajout des deux catégories dans le résultat final
//        result.put("Vulnerabilites", vulnerabilityStats);
//        result.put("Sexes", genderStats);
//
//        return result;
//    }

    /*
    public List<DatabasePapPlaceAffaireResponseDTO> getAllDatabasePapPlaceAffaire(int page, int size) {
        var pageRequest = PageRequest.of(page, size);
        var pageResult = repository.findAll(pageRequest);

        return pageResult.getContent().stream()
                .map(this::convertEntityToResponseDTO)
                .collect(Collectors.toList());
    }


     */


    @Override
    public List<DatabasePapPlaceAffaireResponseDTO> getAllDatabasePapPlaceAffaire(int page, int size) {
        var pageRequest = PageRequest.of(page, size);
        var pageResult = repository.findAll(pageRequest);
        List<DatabasePapPlaceAffaireResponseDTO> data = pageResult.getContent().stream()
                .map(this::convertEntityToResponseDTO)
                .collect(Collectors.toList());

        return data;
    }

    @Override
    public List<DatabasePapPlaceAffaireResponseDTO> getDatabasePapPlaceAffaireByProjectId(Long projectId,int page, int size) {
        var pageRequest = PageRequest.of(page, size);
        var pageResult = repository.findByProjectId(projectId,pageRequest);
        List<DatabasePapPlaceAffaireResponseDTO> data = pageResult.getContent().stream()
                .map(this::convertEntityToResponseDTO)
                .collect(Collectors.toList());
        return data;
    }



    @Override
    public DatabasePapPlaceAffaireResponseDTO getDatabasePapPlaceAffaireById(Long id) {
        DatabasePapPlaceAffaire entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Entity not found"));
        return modelMapper.map(entity, DatabasePapPlaceAffaireResponseDTO.class);
    }

    @Override
    public void updateDatabasePapPlaceAffaire(Long id, DatabasePapPlaceAffaireRequestDTO requestDTO) {
        DatabasePapPlaceAffaire entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Entity not found"));
        modelMapper.typeMap(DatabasePapPlaceAffaireRequestDTO.class, DatabasePapPlaceAffaire.class)
                .addMappings(mapper -> mapper.skip(DatabasePapPlaceAffaire::setId));
        modelMapper.map(requestDTO, entity);
        entity.setType("PAPPLACEAFFAIRE");
        /*
        if (requestDTO.getProjectId() != null) {
            Project project = projectRepository.findById(requestDTO.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found with ID: " + requestDTO.getProjectId()));
            entity.setProject(project);
        }


         */
        entity.setVulnerabilite(determinerVulnerabilite(entity));
        repository.save(entity);
    }

    public long getTotalCount() {
        return repository.count();
    }


    @Override
    public void deleteDatabasePapPlaceAffaire(Long id) {
        DatabasePapPlaceAffaire entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Entity not found"));
        repository.delete(entity);
    }

    private DatabasePapPlaceAffaireResponseDTO convertEntityToResponseDTO(DatabasePapPlaceAffaire entity) {
        DatabasePapPlaceAffaireResponseDTO dto = modelMapper.map(entity, DatabasePapPlaceAffaireResponseDTO.class);
        if (entity.getProject() != null) {
            dto.setProjectId((long) entity.getProject().getId());
        }
        return dto;

    }

    public DatabasePapPlaceAffaire getByCodePap(String codePap) {
        return repository.findByCodePap(codePap)
                .orElseThrow(() -> new EntityNotFoundException("DatabasePapPlaceAffaire avec codePap " + codePap + " introuvable"));
    }

    @Override
    public long getTotalCountByProjectId(Long projectId) {
        return repository.countByProjectId(projectId);
    }

    @Override
    public List<DatabasePapPlaceAffaireResponseDTO> searchGlobalDatabasePapPlaceAffaire(String searchTerm,  Optional<Long> projectId,int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Long projectIdValue = projectId.orElse(null);
        Page<DatabasePapPlaceAffaire> pageResult = repository.searchGlobal(searchTerm,projectIdValue, pageRequest);
        List<DatabasePapPlaceAffaireResponseDTO> data = pageResult.getContent().stream()
                .map(this::convertEntityToResponseDTO)
                .collect(Collectors.toList());
        return data;
    }


    @Override
    public long getTotalCountForSearch(String searchTerm,Optional<Long> projectId) {
        Long projectIdValue = projectId.orElse(null);
        return repository.countBySearchTermAndProjectId(searchTerm,projectIdValue);
    }
}
