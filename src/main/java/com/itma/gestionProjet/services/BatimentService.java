package com.itma.gestionProjet.services;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.entities.Batiment;
import com.itma.gestionProjet.requests.RequestBatiment;

import java.util.List;
import java.util.Optional;

public interface BatimentService {
    AApiResponse<Batiment> getAllBatiments(int offset, int max);
    Batiment createBatiment(List<RequestBatiment> batiment);
    AApiResponse<Batiment> getBatimentById(Long id);
    AApiResponse<Batiment> updateBatiment(Long id, Batiment batiment);
    AApiResponse<Void> deleteBatiment(Long id);


    AApiResponse<List<Batiment>> getBatimentsByCodePap(String codePap, int offset, int max);
}
