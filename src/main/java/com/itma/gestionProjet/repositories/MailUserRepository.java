package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.MailUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailUserRepository extends JpaRepository<MailUser, Long> {
}
