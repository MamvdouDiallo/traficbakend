package com.itma.gestionProjet.Password;


import com.itma.gestionProjet.entities.User;
import com.itma.gestionProjet.exceptions.EmailAlreadySendException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;


    public void createPasswordResetTokenForUser(User user, String passwordToken) {
        Optional<PasswordResetToken> existingToken = passwordResetTokenRepository.findByUserId((long) user.getId());
        if (existingToken.isPresent()) {
            throw new EmailAlreadySendException("Un mail vous est déja envoyé pour réinitialiser votre mot de passe!!");
           // PasswordResetToken token = existingToken.get();
           // token.setToken(passwordToken);
          //  passwordResetTokenRepository.save(token);
        } else {
            PasswordResetToken passwordResetToken = new PasswordResetToken(passwordToken, user);
            passwordResetTokenRepository.save(passwordResetToken);
        }
    }


    public String validatePasswordResetToken(String passwordResetToken) {
        PasswordResetToken passwordToken = passwordResetTokenRepository.findByToken(passwordResetToken);
        if(passwordToken == null){
            return "Invalid verification token";
        }
        User user = passwordToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((passwordToken.getExpirationTime().getTime()-calendar.getTime().getTime())<= 0){
            return "Link already expired, resend link";
        }
        return "valid";
    }
    public Optional<User> findUserByPasswordToken(String passwordResetToken) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(passwordResetToken).getUser());
    }

    public void deletePasswordResetToken(String token){
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);
         passwordResetTokenRepository.delete(resetToken);
    }
    public PasswordResetToken findPasswordResetToken(String token){
        return passwordResetTokenRepository.findByToken(token);
    }

}