package com.itma.gestionProjet.repositories;

import com.itma.gestionProjet.dtos.UserDTO;
import com.itma.gestionProjet.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByContact(String contact);


    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
    List<User> findUsersByRoleName(@Param("roleName") String roleName);

    User findById(Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_project WHERE user_id = :userId", nativeQuery = true)
    void deleteUserAssociations(@Param("userId") Long userId);
}