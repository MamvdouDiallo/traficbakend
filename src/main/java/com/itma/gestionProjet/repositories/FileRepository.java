package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository  extends JpaRepository<File,Long> {
}
