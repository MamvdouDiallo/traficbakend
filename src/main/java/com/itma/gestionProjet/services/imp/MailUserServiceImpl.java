package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.MailUserDTO;
import com.itma.gestionProjet.entities.MailUser;
import com.itma.gestionProjet.repositories.MailUserRepository;
import com.itma.gestionProjet.services.MailUserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MailUserServiceImpl implements MailUserService {

    @Autowired
    private MailUserRepository mailUserRepository;

    @Override
    public List<MailUserDTO> getAllMailUsers(int offset, int max) {
        return mailUserRepository.findAll()
                .stream()
                .skip(offset)
                .limit(max)
                .map(mailUser -> {
                    MailUserDTO dto = new MailUserDTO();
                    dto.setId(mailUser.getId());
                    dto.setNomComplet(mailUser.getNomComplet());
                    dto.setEmail(mailUser.getEmail());
                    dto.setNumeroTelephone(mailUser.getNumeroTelephone());
                    dto.setObject(mailUser.getObject());
                    dto.setMotif(mailUser.getMotif());
                    dto.setContenu(mailUser.getContenu());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public MailUserDTO getMailUserById(Long id) {
        MailUser mailUser = mailUserRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        MailUserDTO dto = new MailUserDTO();
        dto.setId(mailUser.getId());
        dto.setNomComplet(mailUser.getNomComplet());
        dto.setEmail(mailUser.getEmail());
        dto.setNumeroTelephone(mailUser.getNumeroTelephone());
        dto.setObject(mailUser.getObject());
        dto.setMotif(mailUser.getMotif());
        dto.setContenu(mailUser.getContenu());
        return dto;
    }

    /*
    @Override
    public MailUserDTO createMailUser(MailUserDTO mailUserDTO) {
        MailUser mailUser = new MailUser();
        mailUser.setNomComplet(mailUserDTO.getNomComplet());
        mailUser.setEmail(mailUserDTO.getEmail());
        mailUser.setNumeroTelephone(mailUserDTO.getNumeroTelephone());
        mailUser.setObject(mailUserDTO.getObject());
        mailUser.setMotif(mailUserDTO.getMotif());
        mailUser.setContenu(mailUserDTO.getContenu());
        mailUser = mailUserRepository.save(mailUser);

        mailUserDTO.setId(mailUser.getId());
        return mailUserDTO;
    }

     */
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public MailUserDTO createMailUser(MailUserDTO mailUserDTO) {
        MailUser mailUser = new MailUser();
        mailUser.setNomComplet(mailUserDTO.getNomComplet());
        mailUser.setEmail(mailUserDTO.getEmail());
        mailUser.setNumeroTelephone(mailUserDTO.getNumeroTelephone());
        mailUser.setObject(mailUserDTO.getObject());
        mailUser.setMotif(mailUserDTO.getMotif());
        mailUser.setContenu(mailUserDTO.getContenu());
        mailUser.setDateCreation(mailUserDTO.getDateCreation());
        mailUser = mailUserRepository.save(mailUser);
        try {
            sendMailToAdmin(mailUserDTO);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mailUserDTO.setId(mailUser.getId());
        return mailUserDTO;
    }

    private void sendMailToAdmin(MailUserDTO mailUserDTO) throws MessagingException, UnsupportedEncodingException {
        String subject = "New Complaint/Message from: " + mailUserDTO.getNomComplet();
        String senderName = "Application - Support Team";

        // Contenu de l'email
        String mailContent = "<p><b>Nom Complet:</b> " + mailUserDTO.getNomComplet() + "</p>"
                + "<p><b>Email:</b> " + mailUserDTO.getEmail() + "</p>"
                + "<p><b>Numéro de téléphone:</b> " + mailUserDTO.getNumeroTelephone() + "</p>"
                + "<p><b>Objet:</b> " + mailUserDTO.getObject() + "</p>"
                + "<p><b>Motif:</b> " + mailUserDTO.getMotif() + "</p>"
                + "<p><b>Contenu:</b></p>"
                + "<p>" + mailUserDTO.getContenu() + "</p>"
                + "<p><i>Date de réclamation:</i> " + mailUserDTO.getDateCreation() + "</p>";
        // Créer un message MIME
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        // Paramètres du message
        messageHelper.setFrom(mailUserDTO.getEmail(), mailUserDTO.getNomComplet());
        messageHelper.setTo("salioufereya19@gmail.com");
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
    @Override
    public void deleteMailUser(Long id) {
        mailUserRepository.deleteById(id);
    }
}
