package com.itma.gestionProjet.services.imp;


import com.itma.gestionProjet.dtos.PlainteUserDTO;
import com.itma.gestionProjet.entities.PlainteUser;
import com.itma.gestionProjet.repositories.PlainteUserRepository;
import com.itma.gestionProjet.services.PlainteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlainteUserServiceImpl implements PlainteUserService {

    private final PlainteUserRepository plainteUserRepository;

    @Autowired
    public PlainteUserServiceImpl(PlainteUserRepository plainteUserRepository) {
        this.plainteUserRepository = plainteUserRepository;
    }

    @Override
    public PlainteUser createComplaint(PlainteUserDTO plainteUserDTO) {
        PlainteUser plainteUser = new PlainteUser();
        plainteUser.setFirstName(plainteUserDTO.getFirstName());
        plainteUser.setLastName(plainteUserDTO.getLastName());
        plainteUser.setEmail(plainteUserDTO.getEmail());
        plainteUser.setPhone(plainteUserDTO.getPhone());
        plainteUser.setComplaintType(plainteUserDTO.getComplaintType());
        plainteUser.setComplaintDescription(plainteUserDTO.getComplaintDescription());
        plainteUser.setEtat(plainteUserDTO.getEtat());

        return plainteUserRepository.save(plainteUser);
    }

    @Override
    public List<PlainteUser> getAllComplaints() {
        return plainteUserRepository.findAll();
    }
}
