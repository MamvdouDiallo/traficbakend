package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.ConsultantResponse;
import com.itma.gestionProjet.dtos.TacheDTO;
import com.itma.gestionProjet.dtos.TacheResponseDTO;
import com.itma.gestionProjet.dtos.UserDTO;
import com.itma.gestionProjet.entities.Tache;
import com.itma.gestionProjet.entities.User;
import com.itma.gestionProjet.repositories.TacheRepository;
import com.itma.gestionProjet.services.ITacheService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TacheServiceImpl implements ITacheService {

    @Autowired
    private TacheRepository tacheRepository;

    @Override
    public Tache createTache(Tache tache) {
        return tacheRepository.save(tache);
    }

    @Override
    public Tache updateTache(Long id, Tache tache) {
        tache.setId(id);
        return tacheRepository.save(tache);
    }

    @Override
    public void deleteTache(Long id) {
        tacheRepository.deleteById(id);
    }

    @Override
    public List<Tache> getAllTaches() {
        return tacheRepository.findAll();
    }

    @Override
    public Tache getTacheById(Long id) {
        Optional<Tache> tache = tacheRepository.findById(id);
        return tache.orElse(null);
    }

    @Override
    public Page<TacheDTO> getAllTaches(PageRequest pageRequest) {
        Page<Tache> tachePage = tacheRepository.findAll(pageRequest);
        List<TacheDTO> tacheDTOs = tachePage.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
        return new PageImpl<>(tacheDTOs, pageRequest, tachePage.getTotalElements());
    }

    private TacheDTO convertEntityToDto(Tache tache) {
        TacheDTO dto = new TacheDTO();
        dto.setId(tache.getId());
        dto.setLibelle(tache.getLibelle());
        dto.setDescription(tache.getDescription());
        dto.setDateDebut(tache.getDateDebut());
        dto.setDateFin(tache.getDateFin());
        dto.setStatut(tache.getStatut());
        Set<ConsultantResponse> consultantDTOs = tache.getUtilisateurs().stream()
                .map(this::convertConsultantToDto)
                .collect(Collectors.toSet());
        dto.setUtilisateurs(consultantDTOs);
        return dto;
    }

    private ConsultantResponse convertConsultantToDto(User consultant) {
        ConsultantResponse consultantDTO = new ConsultantResponse();
        consultantDTO.setId(consultant.getId());
        consultantDTO.setLastname(consultant.getLastname());
        consultantDTO.setFirstname(consultant.getFirstname());
        consultantDTO.setEmail(consultant.getEmail());
        consultantDTO.setDate_of_birth(consultant.getDate_of_birth());
        consultantDTO.setPlace_of_birth(consultant.getDate_of_birth());
        consultantDTO.setContact(consultant.getContact());
        consultantDTO.setLocality(consultant.getLocality());
        consultantDTO.setEnabled(consultant.getEnabled());
        consultantDTO.setImage(consultant.getImage());
        return consultantDTO;
    }



    public Page<Tache> getTachesByUserId(Long userId, Pageable pageable) {
        return tacheRepository.findTachesByUserId(userId, pageable);
    }

    public TacheResponseDTO mapToDTO(Tache tache) {
        TacheResponseDTO dto = new TacheResponseDTO();
        dto.setId(tache.getId());
        dto.setLibelle(tache.getLibelle());
        dto.setDescription(tache.getDescription());
        dto.setDateDebut(tache.getDateDebut());
        dto.setDateFin(tache.getDateFin());
        dto.setStatut(tache.getStatut());
        return dto;
    }

}
