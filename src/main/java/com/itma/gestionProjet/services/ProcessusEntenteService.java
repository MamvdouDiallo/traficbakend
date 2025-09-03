package com.itma.gestionProjet.services;

import com.itma.gestionProjet.entities.Entente;
import com.itma.gestionProjet.entities.EtatProcessusEntente;
import com.itma.gestionProjet.entities.StatutEntente;
import com.itma.gestionProjet.repositories.EntenteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ProcessusEntenteService {

    private final EntenteRepository ententeRepository;

    public Entente etablirCompensation(Long ententeId) {
        Entente entente = getEntente(ententeId);
        validateStatutSynchronise(entente);
        entente.setCompensationEtablie(true);
        entente.setDateEtablissementCompensation(LocalDateTime.now());
        entente.setEtatProcessus(EtatProcessusEntente.PAP_A_INFORMER);
        return ententeRepository.save(entente);
    }

    public Entente informerPap(Long ententeId, String modeInformation,String detailsInformation) {
        Entente entente = getEntente(ententeId);

        validateEtapePrecedente(entente.getCompensationEtablie(), "Compensation non établie");
        entente.setDetailsInformation(detailsInformation);
        entente.setPapInformee(true);
        entente.setDateInformationPap(LocalDateTime.now());
        entente.setModeInformation(modeInformation);
        entente.setEtatProcessus(EtatProcessusEntente.ACCORD_A_OBTENIR);

        return ententeRepository.save(entente);
    }

    public Entente obtenirAccordPap(Long ententeId, String preuveAccord) {
        Entente entente = getEntente(ententeId);
        validateEtapePrecedente(entente.getPapInformee(), "PAP non informée");
        entente.setAccordPapObtenu(true);
        entente.setDateAccordPap(LocalDateTime.now());
        entente.setPreuveAccord(preuveAccord);
        entente.setEtatProcessus(EtatProcessusEntente.PAIEMENT_A_EFFECTUER);
        return ententeRepository.save(entente);
    }



    public Entente effectuerPaiement(Long ententeId, String referencePaiement) {
        Entente entente = getEntente(ententeId);

        validateEtapePrecedente(entente.getAccordPapObtenu(), "Accord PAP non obtenu");
        entente.setPaiementEffectue(true);
        entente.setDatePaiement(LocalDateTime.now());
//        entente.setOptionPaiement(modePaiement);
        entente.setReferencePaiement(referencePaiement);
        entente.setEtatProcessus(EtatProcessusEntente.FORMATION_A_DONNER);
        return ententeRepository.save(entente);
    }

    public Entente donnerFormation(Long ententeId, String typeFormation, String formateur) {
        Entente entente = getEntente(ententeId);
//        validatePaiementEspece(entente);
        validateEtapePrecedente(entente.getPaiementEffectue(), "Paiement non effectué");
        entente.setFormationDonnee(true);
        entente.setDateFormation(LocalDateTime.now());
        entente.setTypeFormation(typeFormation);
        entente.setFormateur(formateur);
        entente.setEtatProcessus(EtatProcessusEntente.SUIVI_A_EFFECTUER);

        return ententeRepository.save(entente);
    }

    public Entente effectuerSuivi(Long ententeId, String resultatSuivi, String commentairesSuivi) {
        Entente entente = getEntente(ententeId);

        validateEtapePrecedente(entente.getPaiementEffectue(), "Paiement non effectué");

//        if ("ESPECE".equalsIgnoreCase(entente.getOptionPaiement())) {
//            validateEtapePrecedente(entente.getFormationDonnee(), "Formation non donnée");
//        }

        entente.setSuiviEffectue(true);
        entente.setDateSuivi(LocalDateTime.now());
        entente.setResultatSuivi(resultatSuivi);
        entente.setCommentairesSuivi(commentairesSuivi);
        entente.setEtatProcessus(EtatProcessusEntente.PROCESSUS_TERMINE);
        return ententeRepository.save(entente);
    }

    private Entente getEntente(Long ententeId) {
        return ententeRepository.findById(ententeId)
                .orElseThrow(() -> new RuntimeException("Entente non trouvée"));
    }

    private void validateStatutSynchronise(Entente entente) {
        if (entente.getStatut() != StatutEntente.SYNCHRONISEE) {
            throw new IllegalStateException("L'entente doit être synchronisée");
        }
    }

    private void validateEtapePrecedente(Boolean etape, String message) {
        if (!Boolean.TRUE.equals(etape)) {
            throw new IllegalStateException(message);
        }
    }

    private void validatePaiementEspece(Entente entente) {
        if (!"ESPECE".equalsIgnoreCase(entente.getOptionPaiement())) {
            throw new IllegalStateException("La formation n'est requise que pour les paiements en espèce");
        }
    }

    public void validerProcessusComplet(Long ententeId) {
        Entente entente = getEntente(ententeId);

        if (!entente.getCompensationEtablie()) throw new IllegalStateException("Compensation non établie");
        if (!entente.getPapInformee()) throw new IllegalStateException("PAP non informée");
        if (!entente.getAccordPapObtenu()) throw new IllegalStateException("Accord non obtenu");
        if (!entente.getPaiementEffectue()) throw new IllegalStateException("Paiement non effectué");
        if ("ESPECE".equalsIgnoreCase(entente.getOptionPaiement()) && !entente.getFormationDonnee()) {
            throw new IllegalStateException("Formation non donnée");
        }
        if (!entente.getSuiviEffectue()) throw new IllegalStateException("Suivi non effectué");
    }
}
