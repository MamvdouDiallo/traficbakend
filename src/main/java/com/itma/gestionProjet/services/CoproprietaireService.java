package com.itma.gestionProjet.services;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.entities.Coproprietaire;
import com.itma.gestionProjet.requests.CoproprietaireRequest;

import java.util.List;

public interface CoproprietaireService {
    AApiResponse<Coproprietaire> getAllCoproprietaires(int offset, int max);
    List<CoproprietaireRequest> createCoproprietaire(List<CoproprietaireRequest> coproprietaireRequests);
    AApiResponse<Coproprietaire> getCoproprietaireById(Long id);
    AApiResponse<Coproprietaire> updateCoproprietaire(Long id, Coproprietaire coproprietaire);
    AApiResponse<Void> deleteCoproprietaire(Long id);



    AApiResponse<List<Coproprietaire>> getCoproprietairesByCodePap(String codePap, int offset, int max);


}