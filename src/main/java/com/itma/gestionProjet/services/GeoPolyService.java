package com.itma.gestionProjet.services;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.entities.Betails;
import com.itma.gestionProjet.entities.GeoPoly;
import com.itma.gestionProjet.requests.GeoPolyRequest;

import java.util.List;

public interface GeoPolyService {
    List<GeoPoly> createGeoPoly(List<GeoPolyRequest> geoPoly);
    AApiResponse<GeoPoly> getAllGeoPolys(int page, int size);
    AApiResponse<GeoPoly> getGeoPolyById(Long id);
    AApiResponse<GeoPoly> updateGeoPoly(Long id, GeoPoly geoPoly);
    AApiResponse<GeoPoly> deleteGeoPoly(Long id);

    AApiResponse<List<GeoPoly>> getGeoPolyByCodePap(String codePap, int offset, int max);
}
