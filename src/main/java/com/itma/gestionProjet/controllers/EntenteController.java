package com.itma.gestionProjet.controllers;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.EntenteDetailsDTO;
import com.itma.gestionProjet.dtos.ModificationEntenteDTO;
import com.itma.gestionProjet.entities.Entente;
import com.itma.gestionProjet.entities.TypePap;
import com.itma.gestionProjet.requests.InformerPapRequest;
import com.itma.gestionProjet.services.EntenteService;
import com.itma.gestionProjet.services.ModificationEntenteService;
import com.itma.gestionProjet.services.ProcessusEntenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/ententeSynchroniser")
@RequiredArgsConstructor
public class EntenteController {
    private final EntenteService ententeService;
    private final ProcessusEntenteService processusService;
    private final ModificationEntenteService modificationService;
    @PostMapping
    public ResponseEntity<?> createEntente(
            @RequestParam Long papId,
            @RequestParam String papType,
            @RequestParam Long projectId) {
        try {
            TypePap typePapEnum = TypePap.valueOf(papType.toUpperCase());

            Entente nouvelleEntente = ententeService.createEntente(
                    papId,
                    typePapEnum,
                    projectId
            );
            return ResponseEntity.ok(nouvelleEntente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    "TypePap invalide: '" + papType + "'. " +
                            "Valeurs valides: " + Arrays.toString(TypePap.values())
            );
        }
    }

    @GetMapping()
    public ResponseEntity<AApiResponse<EntenteDetailsDTO>> getEntentesByProject(
            @RequestParam(required = false) Long projectId,
            @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<EntenteDetailsDTO> ententesPage = ententeService.getEntentesByProject(projectId, pageable);
        AApiResponse<EntenteDetailsDTO> response = new AApiResponse<>();
        response.setResponseCode(200);
        response.setData(ententesPage.getContent());
        response.setOffset(ententesPage.getPageable().getPageNumber());
        response.setMax(ententesPage.getPageable().getPageSize());
        response.setLength(ententesPage.getTotalElements());
        response.setMessage("Successfully retrieved ententes for project: " + projectId);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EntenteDetailsDTO> getEntenteDetails(@PathVariable Long id) {
        return ResponseEntity.ok(ententeService.getEntenteDetails(id));
    }

//    @PostMapping("/{id}/synchroniser")
//    public ResponseEntity<Entente> synchroniserEntente(@PathVariable Long id) {
//        return ResponseEntity.ok(ententeService.synchroniserEntente(id));
//    }
//
//    @PostMapping("/{id}/finaliser")
//    public ResponseEntity<Entente> finaliserEntente(@PathVariable Long id) {
//        return ResponseEntity.ok(ententeService.finaliserEntente(id));
//    }

    @PostMapping("/{id}/synchroniser")
    public ResponseEntity<AApiResponse<Entente>> synchroniserEntente(@PathVariable Long id) {
        Entente ententeSynchronisee = ententeService.synchroniserEntente(id);
        AApiResponse<Entente> response = new AApiResponse<>();
        response.setResponseCode(200);
        response.setData(List.of(ententeSynchronisee));
        response.setOffset(0);
        response.setMax(1);
        response.setLength(1);
        response.setMessage("Entente synchronisée avec succès");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/finaliser")
    public ResponseEntity<AApiResponse<Entente>> finaliserEntente(@PathVariable Long id) {
        Entente ententeFinalisee = ententeService.finaliserEntente(id);
        AApiResponse<Entente> response = new AApiResponse<>();
        response.setResponseCode(200);
        response.setData(List.of(ententeFinalisee));
        response.setOffset(0);
        response.setMax(1);
        response.setLength(1);
        response.setMessage("Entente finalisée avec succès");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/processus/etablir-compensation")
    public ResponseEntity<Entente> etablirCompensation(@PathVariable Long id) {
        return ResponseEntity.ok(processusService.etablirCompensation(id));
    }

    @PostMapping("/{id}/processus/informer-pap")
    public ResponseEntity<Entente> informerPap(
            @PathVariable Long id,
            @RequestBody InformerPapRequest request) {
        String modeInformation = request.getModeInformation();
        String detailsInformation = request.getDetailsInformation();
        return ResponseEntity.ok(processusService.informerPap(id, modeInformation,detailsInformation));
    }


    @PostMapping("/{ententeId}/processus/obtenir-accord")
    public ResponseEntity<Entente> obtenirAccord(
            @PathVariable Long ententeId,
            @RequestParam String preuveAccord) {
        return ResponseEntity.ok(processusService.obtenirAccordPap(ententeId, preuveAccord));
    }

    @PostMapping("/{ententeId}/processus/effectuer-paiement")
    public ResponseEntity<Entente> effectuerPaiement(
            @PathVariable Long ententeId,
//            @RequestParam String modePaiement,
            @RequestParam String referencePaiement) {
        return ResponseEntity.ok(processusService.effectuerPaiement(ententeId, referencePaiement));
    }

    @PostMapping("/{ententeId}/processus/donner-formation")
    public ResponseEntity<Entente> donnerFormation(
            @PathVariable Long ententeId,
            @RequestParam String typeFormation,
            @RequestParam String formateur) {
        return ResponseEntity.ok(processusService.donnerFormation(ententeId, typeFormation, formateur));
    }

    @PostMapping("/{ententeId}/processus/effectuer-suivi")
    public ResponseEntity<Entente> effectuerSuivi(
            @PathVariable Long ententeId,
            @RequestParam String resultatSuivi,
            @RequestParam String commentairesSuivi) {
        return ResponseEntity.ok(processusService.effectuerSuivi(ententeId, resultatSuivi, commentairesSuivi));
    }

    @PatchMapping("/{ententeId}/edit-ententes")
    public ResponseEntity<AApiResponse<Entente>> modifierEntente(
            @PathVariable Long ententeId,
            @RequestBody ModificationEntenteDTO request) {

        ModificationEntenteDTO dto = new ModificationEntenteDTO();
        dto.setEntenteId(ententeId);
        dto.setModifications(request.getModifications());
        dto.setRaisonModification(request.getRaisonModification());
        dto.setEmail(request.getEmail());

        Entente ententeModifiee = modificationService.modifierValeurs(dto);

        // Création de la réponse au format AApiResponse
        AApiResponse<Entente> response = new AApiResponse<>();
        response.setResponseCode(200);
        response.setData(List.of(ententeModifiee)); // Mettre l'entente modifiée dans une liste
        response.setOffset(0); // Pas de pagination pour une modification unique
        response.setMax(1); // Une seule entité retournée
        response.setLength(1); // Total d'éléments = 1
        response.setMessage("Entente modifiée avec succès");

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{ententeId}/mode-paiement")
    public ResponseEntity<Entente> modifierModePaiement(
            @PathVariable Long ententeId,
            @RequestParam String modePaiement,
            @RequestParam String raisonModification,
            @RequestParam String utilisateur) {

        Map<String, Object> modifications = new HashMap<>();
        modifications.put("modePaiement", modePaiement);

        ModificationEntenteDTO dto = new ModificationEntenteDTO();
        dto.setEntenteId(ententeId);
        dto.setModifications(modifications);
        dto.setRaisonModification(raisonModification);
        dto.setEmail(utilisateur);

        Entente ententeModifiee = modificationService.modifierValeurs(dto);
        return ResponseEntity.ok(ententeModifiee);
    }


    @GetMapping("/byCodePap")
    public ResponseEntity<AApiResponse<EntenteDetailsDTO>> getEntenteByCodePap(@RequestParam String codePap) {
        try {
            EntenteDetailsDTO entente = ententeService.getEntenteByCodePap(codePap);
            List<EntenteDetailsDTO> data = Collections.singletonList(entente);

            AApiResponse<EntenteDetailsDTO> response = new AApiResponse<>();
            response.setResponseCode(200);
            response.setData(data);
            response.setOffset(0);
            response.setMax(1);
            response.setLength(1);
            response.setMessage("Entente récupérée avec succès");

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            AApiResponse<EntenteDetailsDTO> response = new AApiResponse<>();
            response.setResponseCode(404);
            response.setData(Collections.emptyList());
            response.setOffset(0);
            response.setMax(0);
            response.setLength(0);
            response.setMessage(e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
