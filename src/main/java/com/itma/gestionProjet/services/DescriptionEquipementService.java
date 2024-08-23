package com.itma.gestionProjet.services;


import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.entities.Coproprietaire;
import com.itma.gestionProjet.entities.DescriptionEquipement;
import com.itma.gestionProjet.requests.DescriptionEquipementRequest;

import java.util.List;

public interface DescriptionEquipementService {
    List<DescriptionEquipement> createDescriptionEquipement(List<DescriptionEquipementRequest> descriptionEquipement);
    AApiResponse<DescriptionEquipement> getAllDescriptionEquipements(int page, int size);
    AApiResponse<DescriptionEquipement> getDescriptionEquipementById(Long id);
    AApiResponse<DescriptionEquipement> updateDescriptionEquipement(Long id, DescriptionEquipement descriptionEquipement);
    AApiResponse<DescriptionEquipement> deleteDescriptionEquipement(Long id);
    AApiResponse<List<DescriptionEquipement>> getEquipementByCodePap(String codePap, int offset, int max);
}
