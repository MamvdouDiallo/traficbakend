package com.itma.gestionProjet.repositories;


import com.itma.gestionProjet.entities.EmployePap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployePapRepository extends JpaRepository<EmployePap, Long> {

    List<EmployePap> findByCodePap(String codePap);
}