package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.DatabasePapHabitatRequestDTO;
import com.itma.gestionProjet.dtos.DatabasePapHabitatResponseDTO;
import com.itma.gestionProjet.entities.DatabasePapHabitat;
import com.itma.gestionProjet.entities.Project;
import com.itma.gestionProjet.repositories.DatabasePapHabitatRepository;
import com.itma.gestionProjet.repositories.ProjectRepository;
import com.itma.gestionProjet.services.DatabasePapHabitatService;
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
public class DatabasePapHabitatServiceImpl implements DatabasePapHabitatService {

    @Autowired
    private DatabasePapHabitatRepository repository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void create(List<DatabasePapHabitatRequestDTO> requestDTOs) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.typeMap(DatabasePapHabitatRequestDTO.class, DatabasePapHabitat.class)
                .addMappings(mapper -> mapper.skip(DatabasePapHabitat::setId));

        List<DatabasePapHabitat> entities = requestDTOs.stream().map(dto -> {
            DatabasePapHabitat entity = modelMapper.map(dto, DatabasePapHabitat.class);

            if (entity.getType() == null || entity.getType().isEmpty()) entity.setType("PAPHABITAT");

            if (dto.getProjectId() != null) {
                Project project = projectRepository.findById(dto.getProjectId())
                        .orElseThrow(() -> new RuntimeException("Project not found with ID: " + dto.getProjectId()));
                entity.setProject(project);
            }

            entity.setVulnerabilite(determinerVulnerabilite(entity));
            if (entity.getPerteTotale() == null) entity.setPerteTotale(calculatePerteTotale(entity));

            return entity;
        }).collect(Collectors.toList());

        repository.saveAll(entities);
    }

//    private Double calculatePerteTotale(DatabasePapHabitat entity) {
//        Double perteBatiment = zeroIfNull(entity.getPerteBatiment());
//        Double perteMobiliers = zeroIfNull(entity.getPerteMobiliers());
//        Double perteCloture = zeroIfNull(entity.getPerteCloture());
//        Double fraisDeplacement = zeroIfNull(entity.getFraisDeplacement());
//        return perteBatiment + perteMobiliers + perteCloture + fraisDeplacement;
//    }

    private Double calculatePerteTotale(DatabasePapHabitat entity) {
        // Initialiser toutes les valeurs à 0 si elles sont null
        Double perteTerre = zeroIfNull(entity.getPerteTerre());
        Double perteArbreJeune = zeroIfNull(entity.getPerteArbreJeune());
        Double perteArbreAdulte = zeroIfNull(entity.getPerteArbreAdulte());
        Double perteEquipement = zeroIfNull(entity.getPerteEquipement());
        Double perteBatiment = zeroIfNull(entity.getPerteBatiment());

        // Calcul spécifique pour l'agricole
        return perteTerre + perteArbreJeune + perteArbreAdulte
                + perteEquipement + perteBatiment;
    }

    private Double zeroIfNull(Double v) { return v != null ? v : 0.0; }

    private String determinerVulnerabilite(DatabasePapHabitat pap) {
        List<String> vulnerabilites = new ArrayList<>();

        if (pap.getSituationMatrimoniale() != null &&
                Arrays.asList("veuf", "veuve", "divorcé", "divorcée", "célibataire")
                        .contains(pap.getSituationMatrimoniale().toLowerCase())) {
            vulnerabilites.add("Situation matrimoniale précaire");
        }

        if (pap.getMembreFoyerHandicape() != null && !pap.getMembreFoyerHandicape().isEmpty() &&
                !"non".equalsIgnoreCase(pap.getMembreFoyerHandicape())) {
            vulnerabilites.add("Ménage avec personne handicapée");
        }

        int age = -1;
        if (pap.getAge() != null) age = Math.toIntExact(pap.getAge());
        else if (pap.getDateNaissance() != null) age = calculerAge(pap.getDateNaissance());

        if (age >= 0) {
            if (age < 18 && "chef de ménage".equalsIgnoreCase(pap.getRoleDansFoyer())) {
                vulnerabilites.add("Mineur chef de ménage");
            }
            if (age >= 65 && (pap.getMembreFoyer() == null || pap.getMembreFoyer().isEmpty() || "seul".equalsIgnoreCase(pap.getMembreFoyer()))) {
                vulnerabilites.add("Personne âgée sans soutien");
            }
        }

        if (pap.getMembreFoyer() != null && !pap.getMembreFoyer().isEmpty()) {
            try {
                int nb = Integer.parseInt(pap.getMembreFoyer());
                boolean aActivite = pap.getActivitePrincipale() != null && !pap.getActivitePrincipale().isEmpty();
                if (nb >= 4 && aActivite) vulnerabilites.add("Ménage nombreux");
            } catch (NumberFormatException ignored) {}
        }

        if (pap.getNiveauEtude() != null && (pap.getNiveauEtude().toLowerCase().contains("ne sait pas lire") || "analphabète".equalsIgnoreCase(pap.getNiveauEtude()))) {
            vulnerabilites.add("Analphabétisme");
        }

        return vulnerabilites.isEmpty() ? "Non vulnérable" : String.join(", ", vulnerabilites);
    }

    private int calculerAge(LocalDate dn) { return Period.between(dn, LocalDate.now()).getYears(); }

    @Override
    public Map<String, Object> getVulnerabilityStats(Long projectId) {
        var pageRequest = PageRequest.of(0, 10_000_000);
        Page<DatabasePapHabitat> paged = repository.findByProjectId(projectId, pageRequest);
        List<DatabasePapHabitat> paps = paged.getContent();

        Map<String, Long> globalVulnerabilities = initVulnerabilityMap();
        Map<String, Long> globalGenders = initGenderMap(paps.size());
        Map<String, Map<String, Long>> vulnerabilitiesByGender = initCrossAnalysisMap();

        Set<String> maleTerms = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        maleTerms.addAll(Arrays.asList("m","masculin","homme","male","garçon","h","mâle"));
        Set<String> femaleTerms = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        femaleTerms.addAll(Arrays.asList("f","féminin","femme","femelle","female","fille","feminin"));

        for (DatabasePapHabitat pap : paps) {
            String sexe = pap.getSexe();
            String normalizedSexe = sexe != null ? sexe.trim().toLowerCase() : "";
            String genderKey = getGenderKey(normalizedSexe, maleTerms, femaleTerms);
            globalGenders.put(genderKey, globalGenders.get(genderKey) + 1);

            String vuln = pap.getVulnerabilite();
            boolean isNon = vuln == null || vuln.equals("Non vulnérable");
            if (isNon) {
                globalVulnerabilities.put("Non vulnérable", globalVulnerabilities.get("Non vulnérable") + 1);
                vulnerabilitiesByGender.get("Non vulnérable").put(genderKey, vulnerabilitiesByGender.get("Non vulnérable").get(genderKey) + 1);
                continue;
            }
            for (String category : globalVulnerabilities.keySet()) {
                if (vuln.contains(category)) {
                    globalVulnerabilities.put(category, globalVulnerabilities.get(category) + 1);
                    vulnerabilitiesByGender.get(category).put(genderKey, vulnerabilitiesByGender.get(category).get(genderKey) + 1);
                }
            }
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("Vulnerabilites_globales", globalVulnerabilities);
        result.put("Sexes_globaux", globalGenders);
        result.put("Vulnerabilites_par_sexe", vulnerabilitiesByGender);
        return result;
    }

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

    @Override
    public List<DatabasePapHabitatResponseDTO> getAll(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<DatabasePapHabitat> pageResult = repository.findAll(pageRequest);
        return pageResult.getContent().stream().map(this::convertEntityToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<DatabasePapHabitatResponseDTO> getByProjectId(Long projectId, int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<DatabasePapHabitat> pageResult = repository.findByProjectId(projectId, pageRequest);
        return pageResult.getContent().stream().map(this::convertEntityToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public DatabasePapHabitatResponseDTO getById(Long id) {
        DatabasePapHabitat entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Entity not found"));
        return modelMapper.map(entity, DatabasePapHabitatResponseDTO.class);
    }

    @Override
    public void update(Long id, DatabasePapHabitatRequestDTO requestDTO) {
        DatabasePapHabitat entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Entity not found with ID: " + id));
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.typeMap(DatabasePapHabitatRequestDTO.class, DatabasePapHabitat.class).addMappings(m -> m.skip(DatabasePapHabitat::setId));
        modelMapper.map(requestDTO, entity);

        if (entity.getType() == null || entity.getType().isEmpty()) entity.setType("PAPHABITAT");
        if (entity.getPerteTotale() == null) entity.setPerteTotale(calculatePerteTotale(entity));

        if (requestDTO.getProjectId() != null) {
            Project project = projectRepository.findById(requestDTO.getProjectId()).orElseThrow(() -> new RuntimeException("Project not found with ID: " + requestDTO.getProjectId()));
            entity.setProject(project);
        }

        entity.setVulnerabilite(determinerVulnerabilite(entity));
        repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        DatabasePapHabitat entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Entity not found"));
        repository.delete(entity);
    }

    private DatabasePapHabitatResponseDTO convertEntityToResponseDTO(DatabasePapHabitat entity) {
        DatabasePapHabitatResponseDTO dto = modelMapper.map(entity, DatabasePapHabitatResponseDTO.class);
        if (entity.getProject() != null) dto.setProjectId(entity.getProject().getId());
        return dto;
    }

    @Override
    public DatabasePapHabitatResponseDTO getByCodePap(String codePap) {
        DatabasePapHabitat entity = repository.findByCodePap(codePap).orElseThrow(() -> new EntityNotFoundException("DatabasePapHabitat with codePap " + codePap + " not found"));
        return modelMapper.map(entity, DatabasePapHabitatResponseDTO.class);
    }

    @Override
    public long getTotalCount() { return repository.count(); }

    @Override
    public long getTotalCountByProjectId(Long projectId) { return repository.countByProjectId(projectId); }

    @Override
    public List<DatabasePapHabitatResponseDTO> searchGlobal(String searchTerm, Optional<Long> projectId, int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Long projectIdValue = projectId.orElse(null);
        Page<DatabasePapHabitat> pageResult = repository.searchGlobal(searchTerm, projectIdValue, pageRequest);
        return pageResult.getContent().stream().map(this::convertEntityToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public long getTotalCountForSearch(String searchTerm, Optional<Long> projectId) {
        Long projectIdValue = projectId.orElse(null);
        return repository.countBySearchTermAndProjectId(searchTerm, projectIdValue);
    }

    @Override
    public Double calculateTotalPerte(Long projectId) {
        if (projectId == null) throw new IllegalArgumentException("Project ID cannot be null");
        long totalItems = repository.countByProjectId(projectId);
        if (totalItems == 0) return 0.0;
        int pageSize = 1000;
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
        double total = 0.0;
        for (int i = 0; i < totalPages; i++) {
            Page<DatabasePapHabitat> page = repository.findByProjectId(projectId, PageRequest.of(i, pageSize));
            total += page.getContent().stream().filter(Objects::nonNull).mapToDouble(p -> p.getPerteTotale() != null ? p.getPerteTotale() : 0.0).sum();
        }
        return total;
    }

    @Override
    public void deleteAllByProjectId(Long projectId) { repository.deleteAllByProjectId(projectId); }

    @Override
    public void deleteAllByIds(List<Long> ids) { repository.deleteAllByIdIn(ids); }
}

