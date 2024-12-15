package com.itma.gestionProjet.services;

import com.itma.gestionProjet.dtos.MailUserDTO;

import java.util.List;

public interface MailUserService {

    List<MailUserDTO> getAllMailUsers(int offset, int max);
    MailUserDTO getMailUserById(Long id);
    MailUserDTO createMailUser(MailUserDTO mailUserDTO);
    void deleteMailUser(Long id);
}
