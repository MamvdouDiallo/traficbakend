package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.Culture;
import com.itma.gestionProjet.entities.DescriptionEquipement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DescriptionEquipementRepository extends JpaRepository<DescriptionEquipement, Long> {

    List<DescriptionEquipement> findByCodePap(String codePap);
}

