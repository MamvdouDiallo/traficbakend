package com.itma.gestionProjet.services;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.BetailsDTO;
import com.itma.gestionProjet.entities.Betails;
import com.itma.gestionProjet.requests.BetailsRequest;

import java.util.List;

public interface BetailsService {
    List<BetailsDTO> createBetails(List<BetailsRequest> betails);
    AApiResponse<Betails> getAllBetails(int page, int size);
    AApiResponse<Betails> getBetailsById(Long id);
    AApiResponse<Betails> updateBetails(Long id, Betails betails);
    AApiResponse<Betails> deleteBetails(Long id);

    AApiResponse<List<Betails>> getBetailsByCodePap(String codePap, int offset, int max);
}

