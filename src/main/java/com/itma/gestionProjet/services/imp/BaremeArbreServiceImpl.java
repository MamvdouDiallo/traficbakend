package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.BaremeArbreDTO;
import com.itma.gestionProjet.entities.BaremeArbre;
import com.itma.gestionProjet.repositories.BaremeArbreRepository;
import com.itma.gestionProjet.requests.BaremeArbreRequest;
import com.itma.gestionProjet.services.BaremeArbreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BaremeArbreServiceImpl implements BaremeArbreService {
    @Autowired
    private BaremeArbreRepository baremeArbreRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AApiResponse<BaremeArbreDTO> createBaremeArbres(List<BaremeArbreRequest> requests) {
        List<BaremeArbre> baremeArbres = requests.stream()
                .map(request -> modelMapper.map(request, BaremeArbre.class))
                .collect(Collectors.toList());

        baremeArbreRepository.saveAll(baremeArbres);
        List<BaremeArbreDTO> baremeArbreDTOs = baremeArbres.stream()
                .map(baremeArbre -> modelMapper.map(baremeArbre, BaremeArbreDTO.class))
                .collect(Collectors.toList());
        int responseCode = 200;  // OK
        String message = "BaremeArbres ajoutés avec succès";
        long length = baremeArbreDTOs.size();
        int offset = 0;
        int max = length > 0 ? (int) length : 0;

        return new AApiResponse<>(responseCode, baremeArbreDTOs, offset, max, message, length);
    }

    @Override
    public Page<BaremeArbreDTO> getAllBaremeArbre(Pageable pageable) {
        return baremeArbreRepository.findAll(pageable)
                .map(baremeArbre -> modelMapper.map(baremeArbre, BaremeArbreDTO.class));
    }

    @Override
    public BaremeArbreDTO getBaremeArbreById(Long id) {
        BaremeArbre baremeArbre = baremeArbreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BaremeArbre not found"));
        return modelMapper.map(baremeArbre, BaremeArbreDTO.class);
    }

    @Override
    public BaremeArbreDTO updateBaremeArbre(Long id, BaremeArbreRequest request) {
        BaremeArbre baremeArbre = baremeArbreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BaremeArbre not found"));
        modelMapper.map(request, baremeArbre);
        baremeArbreRepository.save(baremeArbre);
        return modelMapper.map(baremeArbre, BaremeArbreDTO.class);
    }

    @Override
    public void deleteBaremeArbre(Long id) {
        BaremeArbre baremeArbre = baremeArbreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BaremeArbre not found"));
        baremeArbreRepository.delete(baremeArbre);
    }
}
