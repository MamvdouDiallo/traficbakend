package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.dtos.MailRequestDto;
import com.itma.gestionProjet.dtos.MailUserDTO;
import com.itma.gestionProjet.entities.MailUser;
import com.itma.gestionProjet.repositories.MailUserRepository;
import com.itma.gestionProjet.services.MailUserService;
import com.itma.gestionProjet.utils.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MailUserServiceImpl implements MailUserService {

    @Autowired
    private MailUserRepository mailUserRepository;

    @Autowired
    private EmailService emailService;

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
        dto.setContenu(mailUser.getContenu());
        return dto;
    }

    @Autowired
    private JavaMailSender mailSender;
    @Override
    public MailUserDTO createMailUser(MailUserDTO mailUserDTO) {
        MailUser mailUser = new MailUser();
        mailUser.setNomComplet(mailUserDTO.getNomComplet());
        mailUser.setEmail(mailUserDTO.getEmail());
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
        log.info("Sending mail to {}", mailUserDTO.getEmail());
        String subject = "New Complaint/Message from: " + mailUserDTO.getNomComplet();
        String senderName = "Application - Support Team";
        // Contenu de l'email
        String mailContent = "<strong>Nom Complet:</strong> " + mailUserDTO.getNomComplet() + "<br/>"
                + "<strong>Email:</strong> " + mailUserDTO.getEmail() + "<br/>"
                + "<strong>Contenu:</strong> "+mailUserDTO.getContenu() + "<br/>"
                + "<strong>Date de réclamation: </strong>" + mailUserDTO.getDateCreation();
        MailRequestDto mailRequestDto = new MailRequestDto();
        mailRequestDto.setSubject(subject);
        mailRequestDto.setToEmail("invodis@gmail.com");
        mailRequestDto.setMessage(mailContent);
        mailRequestDto.setTemplate("contact_email");
        emailService.sendMail(mailRequestDto);

        //replay email
        /* ---------------fin info mail admin*/
        /* info replay mail*/
        MailRequestDto request = new MailRequestDto();
        request.setToEmail(mailUserDTO.getEmail());
        request.setSubject("Accusé de réception");
        String message = "Bonjour "+mailUserDTO.getNomComplet()+",<br>" +
                "Nous vous confirmons la réception de votre demande envoyée le "+new Date() +".<br/>" +
                "Ceci est un message automatique pour vous informer que votre requête a bien été enregistrée. Notre équipe l’examinera et reviendra vers vous dans les plus brefs délais si nécessaire.<br/>" +
                "Veuillez noter qu’il n’est pas nécessaire de répondre à cet email.<br/>" +
                "Merci de votre confiance.<br/>";
        request.setMessage(message);
        request.setTemplate("replay_mail");
        emailService.sendMailReception(request);
        /* fin info replay mail*/
    }
    @Override
    public void deleteMailUser(Long id) {
        mailUserRepository.deleteById(id);
    }
}
