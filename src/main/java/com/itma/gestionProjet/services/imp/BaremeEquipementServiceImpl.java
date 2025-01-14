package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.BaremeEquipementDTO;
import com.itma.gestionProjet.entities.BaremeEquipement;
import com.itma.gestionProjet.repositories.BaremeEquipementRepository;
import com.itma.gestionProjet.requests.BaremeEquipementRequest;
import com.itma.gestionProjet.services.BaremeEquipementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BaremeEquipementServiceImpl implements BaremeEquipementService {
    @Autowired
    private BaremeEquipementRepository baremeEquipementRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<BaremeEquipementDTO> createBaremeEquipements(List<BaremeEquipementRequest> requests) {
        try {
            List<BaremeEquipement> baremeEquipements = requests.stream()
                    .map(request -> modelMapper.map(request, BaremeEquipement.class))
                    .collect(Collectors.toList());
            baremeEquipementRepository.saveAll(baremeEquipements);
            return baremeEquipements.stream()
                    .map(baremeEquipement -> modelMapper.map(baremeEquipement, BaremeEquipementDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'ajout des équipements", e);
        }
    }
    @Override
    public Page<BaremeEquipementDTO> getAllBaremeEquipements(Pageable pageable) {
        try {
            Page<BaremeEquipement> page = baremeEquipementRepository.findAll(pageable);
            return page.map(baremeEquipement -> modelMapper.map(baremeEquipement, BaremeEquipementDTO.class));
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des équipements", e);
        }
    }
    @Override
    public BaremeEquipementDTO getBaremeEquipementById(Long id) {
        try {
            BaremeEquipement baremeEquipement = baremeEquipementRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Equipement non trouvé"));
            return modelMapper.map(baremeEquipement, BaremeEquipementDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération de l'équipement", e);
        }
    }
    @Override
    public BaremeEquipementDTO updateBaremeEquipement(Long id, BaremeEquipementRequest request) {
        try {
            BaremeEquipement baremeEquipement = baremeEquipementRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Equipement non trouvé"));
            modelMapper.map(request, baremeEquipement);
            baremeEquipementRepository.save(baremeEquipement);
            return modelMapper.map(baremeEquipement, BaremeEquipementDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la mise à jour de l'équipement", e);
        }
    }
    @Override
    public void deleteBaremeEquipement(Long id) {
        try {
            baremeEquipementRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression de l'équipement", e);
        }
    }
}
