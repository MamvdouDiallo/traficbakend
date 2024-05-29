package com.itma.gestionProjet.Password;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String passwordResetToken);
    PasswordResetToken deleteByToken(String passwordResetToken);
    Optional<PasswordResetToken> findByUserId(Long userId);
}
