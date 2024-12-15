package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.PlainteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlainteUserRepository extends JpaRepository<PlainteUser, Long> {
}
