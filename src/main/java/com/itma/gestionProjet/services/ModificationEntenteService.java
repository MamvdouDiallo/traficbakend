package com.itma.gestionProjet.services;


import com.itma.gestionProjet.dtos.ModificationEntenteDTO;
import com.itma.gestionProjet.entities.*;
import com.itma.gestionProjet.repositories.AuditRepository;
import com.itma.gestionProjet.repositories.DatabasePapAgricoleRepository;
import com.itma.gestionProjet.repositories.DatabasePapPlaceAffaireRepository;
import com.itma.gestionProjet.repositories.EntenteRepository;
import com.itma.gestionProjet.services.imp.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ModificationEntenteService {

    private final EntenteRepository ententeRepository;
    private final DatabasePapAgricoleRepository papAgricoleRepository;
    private final DatabasePapPlaceAffaireRepository papPlaceAffaireRepository;
    private final AuditRepository auditRepository;
    private final ProcessusEntenteService processusService;


    @Autowired
    private UserService userService;

    @Transactional
    public Entente modifierValeurs(ModificationEntenteDTO modificationDTO) {
        Entente entente = ententeRepository.findById(modificationDTO.getEntenteId())
                .orElseThrow(() -> new RuntimeException("Entente non trouvée"));

        // Validation: ne peut modifier que si l'entente n'est pas finalisée
        if (entente.getStatut() == StatutEntente.FINALISEE) {
            throw new IllegalStateException("Impossible de modifier une entente finalisée");
        }

        // 2. Mettre à jour le PAP source avec les mêmes valeurs
        modifierValeursPapSource(entente.getPapId(), modificationDTO.getModifications(),"user", modificationDTO.getEmail());
        // Si des valeurs de compensation sont modifiées, réinitialiser certaines étapes
        if (compensationModifiee(modificationDTO)) {
            reinitialiserEtapesApresCompensation(entente);
        }
        return ententeRepository.save(entente);
    }


    private Object convertirValeur(Object valeur, Class<?> type) {
        if (type == Double.class || type == double.class) {
            return Double.parseDouble(valeur.toString());
        } else if (type == Integer.class || type == int.class) {
            return Integer.parseInt(valeur.toString());
        } else if (type == Boolean.class || type == boolean.class) {
            return Boolean.parseBoolean(valeur.toString());
        } else {
            return valeur.toString();
        }
    }

    private boolean compensationModifiee(ModificationEntenteDTO dto) {
        return dto.getModifications().keySet().stream()
                .anyMatch(champ -> champ.toLowerCase().contains("perte") ||
                        champ.toLowerCase().contains("compensation"));
    }

    private void reinitialiserEtapesApresCompensation(Entente entente) {
        // Réinitialiser les étapes qui dépendent de la compensation
        entente.setPapInformee(false);
        entente.setAccordPapObtenu(false);
        entente.setPaiementEffectue(false);
        entente.setFormationDonnee(false);
        entente.setSuiviEffectue(false);

        // Revenir à l'état approprié
        entente.setEtatProcessus(entente.getCompensationEtablie() ?
                EtatProcessusEntente.PAP_A_INFORMER :
                EtatProcessusEntente.COMPENSATION_A_ETABLIR);
    }

    private boolean utilisateurHasPermission(String emailUtilisateur) {
        List<String> rolesAutorises = Arrays.asList("Super Admin", "Admin", "Superviseur");
        return userService.hasPermission(emailUtilisateur, rolesAutorises);
    }

    // Méthode pour modifier les valeurs liées au PAP source (plus risquée)
    @Transactional
    public void modifierValeursPapSource(Long ententeId, Map<String, Object> modifications, String raison, String utilisateur) {
        Entente entente = ententeRepository.findById(ententeId)
                .orElseThrow(() -> new RuntimeException("Entente non trouvée"));

        if (entente.getStatut() == StatutEntente.FINALISEE) {
            throw new IllegalStateException("Impossible de modifier le PAP source d'une entente finalisée");
        }
        // Vérifier les permissions (à adapter selon ton système d'authentification)
        if (!utilisateurHasPermission(utilisateur)) {
            throw new SecurityException("Permissions insuffisantes pour modifier le PAP source");
        }
        // Journaliser l'opération CRITIQUE
        journaliserModificationCritique(entente, modifications, raison, utilisateur);
        // Appliquer les modifications selon le type de PAP
        if (entente.getPapType() == TypePap.PAPAGRICOLE) {
            modifierPapAgricole(entente.getPapId(), modifications, utilisateur);
        } else if (entente.getPapType() == TypePap.PAPPLACEAFFAIRE) {
            modifierPapPlaceAffaire(entente.getPapId(), modifications, utilisateur);
        }

        // Réinitialiser TOUTES les ententes liées à ce PAP
        reinitialiserEntentesLiees(entente.getPapId(), entente.getPapType(), utilisateur);

        // Notifier les responsables
        notifierResponsablesModificationPap(entente, modifications, utilisateur);
    }

    private void modifierPapAgricole(Long papId, Map<String, Object> modifications, String utilisateur) {
        DatabasePapAgricole pap = papAgricoleRepository.findById(papId)
                .orElseThrow(() -> new RuntimeException("PAP Agricole non trouvé"));

        modifications.forEach((champ, valeur) -> {
            try {
                Field field = DatabasePapAgricole.class.getDeclaredField(champ);
                field.setAccessible(true);

                Object ancienneValeur = field.get(pap);
                field.set(pap, convertirValeur(valeur, field.getType()));
                // Journaliser la modification du PAP source
                journaliserModificationPapSource(
                        papId, "AGRICOLE", champ,
                        String.valueOf(ancienneValeur), String.valueOf(valeur),
                        utilisateur
                );
            } catch (Exception e) {
                throw new RuntimeException("Erreur modification PAP Agricole - champ: " + champ, e);
            }
        });
        papAgricoleRepository.save(pap);
    }

    private void modifierPapPlaceAffaire(Long papId, Map<String, Object> modifications, String utilisateur) {
        DatabasePapPlaceAffaire pap = papPlaceAffaireRepository.findById(papId)
                .orElseThrow(() -> new RuntimeException("PAP Place Affaire non trouvé"));

        modifications.forEach((champ, valeur) -> {
            try {
                Field field = DatabasePapPlaceAffaire.class.getDeclaredField(champ);
                field.setAccessible(true);

                Object ancienneValeur = field.get(pap);
                field.set(pap, convertirValeur(valeur, field.getType()));

                // Journaliser la modification du PAP source
                journaliserModificationPapSource(
                        papId, "PLACE_AFFAIRE", champ,
                        String.valueOf(ancienneValeur), String.valueOf(valeur),
                        utilisateur
                );

            } catch (Exception e) {
                throw new RuntimeException("Erreur modification PAP Place Affaire - champ: " + champ, e);
            }
        });

        papPlaceAffaireRepository.save(pap);
    }

    private void reinitialiserEntentesLiees(Long papId, TypePap papType, String utilisateur) {
        List<Entente> ententesLiees = ententeRepository.findByPapIdAndPapType(papId, papType);

        ententesLiees.forEach(entente -> {
            if (entente.getStatut() != StatutEntente.FINALISEE) {
                // Réinitialiser le processus pour les ententes non finalisées
                entente.setCompensationEtablie(false);
                entente.setPapInformee(false);
                entente.setAccordPapObtenu(false);
                entente.setPaiementEffectue(false);
                entente.setFormationDonnee(false);
                entente.setSuiviEffectue(false);
                entente.setEtatProcessus(EtatProcessusEntente.COMPENSATION_A_ETABLIR);

                // Journaliser la réinitialisation
                entente.ajouterModification(
                        "REINITIALISATION_PROCESSUS",
                        "Processus avancé",
                        "Processus réinitialisé",
                        "Système (modification PAP source par " + utilisateur + ")"
                );

                ententeRepository.save(entente);
            }
        });

//        logger.warn("{} ententes réinitialisées après modification PAP source ID: {}",
//                ententesLiees.size(), papId);
    }

    private void journaliserModificationCritique(Entente entente, Map<String, Object> modifications, String raison, String utilisateur) {
        String message = String.format(
                "MODIFICATION CRITIQUE PAP SOURCE - Entente ID: %d, PAP ID: %s, Type: %s, " +
                        "Modifications: %s, Raison: %s, Utilisateur: %s",
                entente.getId(), entente.getPapId(), entente.getPapType(),
                modifications.toString(), raison, utilisateur
        );

        // Journal dans la base
        auditRepository.save(new AuditEntry(
                "MODIFICATION_PAP_SOURCE",
                message,
                utilisateur,
                LocalDateTime.now()
        ));

        // Journal dans les logs
//        logger.error("ALERTE: " + message);
    }

    private void journaliserModificationPapSource(Long papId, String papType, String champ,
                                                  String ancienneValeur, String nouvelleValeur,
                                                  String utilisateur) {
        auditRepository.save(new AuditEntry(
                "MODIFICATION_PAP_CHAMP",
                String.format("PAP %s ID %d - %s: %s -> %s",
                        papType, papId, champ, ancienneValeur, nouvelleValeur),
                utilisateur,
                LocalDateTime.now()
        ));
    }

    private void notifierResponsablesModificationPap(Entente entente, Map<String, Object> modifications, String utilisateur) {
        // Implémentation dépendante de ton système de notification
//        notificationService.envoyerNotification(
//                "ALERTE: Modification PAP Source",
//                String.format("Le PAP source de l'entente %d a été modifié par %s. Modifications: %s",
//                        entente.getId(), utilisateur, modifications.toString()),
//                "RESPONSABLES_MODIFICATION_PAP" // Groupe de destinataires
//        );
    }
}
