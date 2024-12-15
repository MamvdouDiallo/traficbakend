package com.itma.gestionProjet.services;

import com.itma.gestionProjet.dtos.PlainteUserDTO;
import com.itma.gestionProjet.entities.PlainteUser;

import java.util.List;

public interface PlainteUserService {
    PlainteUser createComplaint(PlainteUserDTO plainteUserDTO);
    List<PlainteUser> getAllComplaints();
}