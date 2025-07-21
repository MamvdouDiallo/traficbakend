package com.itma.gestionProjet.controllers;

import com.itma.gestionProjet.dtos.CombinedStatsResponse;
import com.itma.gestionProjet.services.DatabasePapAgricoleService;
import com.itma.gestionProjet.services.DatabasePapPlaceAffaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/stats")
public class statController {
    @Autowired
    private DatabasePapPlaceAffaireService databasePapPlaceAffaireService;

    @Autowired
    private DatabasePapAgricoleService databasePapAgricoleService;
    @GetMapping("/combine")
    public CombinedStatsResponse getCombinedStats( @RequestParam(required = false) Long projectId) {
        // Récupérer les stats séparées
        Map<String, Object> placeAffaire = databasePapPlaceAffaireService.getVulnerabilityStats(projectId);
        Map<String, Object> agricole = databasePapAgricoleService.getVulnerabilityStats(projectId);

        // Créer la réponse combinée
        CombinedStatsResponse response = new CombinedStatsResponse();
        response.setPlaceAffaireStats(placeAffaire);
        response.setAgricoleStats(agricole);
        response.setTotalStats(calculateTotalStats(placeAffaire, agricole));

        return response;
    }

    private Map<String, Object> calculateTotalStats(Map<String, Object> stats1, Map<String, Object> stats2) {
        Map<String, Object> totals = new HashMap<>();

        // Calcul pour Vulnerabilites_globales
        Map<String, Number> vulnGlobal1 = (Map<String, Number>) stats1.get("Vulnerabilites_globales");
        Map<String, Number> vulnGlobal2 = (Map<String, Number>) stats2.get("Vulnerabilites_globales");
        Map<String, Integer> vulnGlobalTotal = new HashMap<>();

        vulnGlobal1.forEach((key, value) ->
                vulnGlobalTotal.put(key, value.intValue() + vulnGlobal2.getOrDefault(key, 0).intValue()));

        // Calcul pour Sexes_globaux
        Map<String, Number> sexes1 = (Map<String, Number>) stats1.get("Sexes_globaux");
        Map<String, Number> sexes2 = (Map<String, Number>) stats2.get("Sexes_globaux");
        Map<String, Integer> sexesTotal = new HashMap<>();

        sexes1.forEach((key, value) ->
                sexesTotal.put(key, value.intValue() + sexes2.getOrDefault(key, 0).intValue()));

        // Calcul pour Vulnerabilites_par_sexe
        Map<String, Map<String, Number>> vulnSexe1 = (Map<String, Map<String, Number>>) stats1.get("Vulnerabilites_par_sexe");
        Map<String, Map<String, Number>> vulnSexe2 = (Map<String, Map<String, Number>>) stats2.get("Vulnerabilites_par_sexe");
        Map<String, Map<String, Integer>> vulnSexeTotal = new HashMap<>();

        vulnSexe1.forEach((vulnKey, sexeMap) -> {
            Map<String, Integer> totalSexeMap = new HashMap<>();
            sexeMap.forEach((sexeKey, value) -> {
                int sum = value.intValue() + vulnSexe2.get(vulnKey).get(sexeKey).intValue();
                totalSexeMap.put(sexeKey, sum);
            });
            vulnSexeTotal.put(vulnKey, totalSexeMap);
        });

        // Construire la réponse totale
        totals.put("Vulnerabilites_globales", vulnGlobalTotal);
        totals.put("Sexes_globaux", sexesTotal);
        totals.put("Vulnerabilites_par_sexe", vulnSexeTotal);

        return totals;
    }
}
