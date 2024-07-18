package com.itma.gestionProjet.services;

import com.itma.gestionProjet.dtos.TacheDTO;
import com.itma.gestionProjet.entities.Tache;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ITacheService {
    Tache createTache(Tache tache);
    Tache updateTache(Long id, Tache tache);
    void deleteTache(Long id);
    List<Tache> getAllTaches();
    Tache getTacheById(Long id);

    Page<TacheDTO> getAllTaches(PageRequest pageRequest);

}
