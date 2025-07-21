package com.itma.gestionProjet.dtos;

import lombok.Data;

import java.util.Map;

@Data
public class CombinedStatsResponse {
    private Map<String, Object> placeAffaireStats;
    private Map<String, Object> agricoleStats;
    private Map<String, Object> totalStats;

}

