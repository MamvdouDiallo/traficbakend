package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.entities.SituationMatrimoniale;
import com.itma.gestionProjet.repositories.SituationMatrimonialeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SituationMatrimonialeService {

    @Autowired
    private SituationMatrimonialeRepository situationMatrimonialeRepository;

    public Page<SituationMatrimoniale> getSituationsMatrimoniales(int offset, int max) {
        Pageable pageable = PageRequest.of(offset, max);  // Pas besoin de cast explicite
        return situationMatrimonialeRepository.findAll(pageable);  // Utilisez directement pageable
    }
}

