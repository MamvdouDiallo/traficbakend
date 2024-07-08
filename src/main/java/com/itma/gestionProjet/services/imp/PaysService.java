package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.entities.Pays;
import com.itma.gestionProjet.repositories.PaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

@Service
public class PaysService {

    @Autowired
    private PaysRepository paysRepository;

    public Page<Pays> getPays(int offset, int max) {
        Pageable pageable = PageRequest.of(offset, max);
        return paysRepository.findAll(pageable);
    }
}
