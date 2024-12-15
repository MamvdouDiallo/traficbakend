package com.itma.gestionProjet.controllers;


import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.MailUserDTO;
import com.itma.gestionProjet.services.MailUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/mailusers")
public class MailUserController {

    @Autowired
    private MailUserService mailUserService;

    @GetMapping
    public AApiResponse<MailUserDTO> getAllMailUsers(@RequestParam int offset, @RequestParam int max) {
        List<MailUserDTO> mailUsers = mailUserService.getAllMailUsers(offset, max);
        AApiResponse<MailUserDTO> response = new AApiResponse<>();
        response.setResponseCode(200);
        response.setData(mailUsers);
        response.setOffset(offset);
        response.setMax(max);
        response.setMessage("Success");
        response.setLength(mailUsers.size());
        return response;
    }

    @GetMapping("/{id}")
    public MailUserDTO getMailUserById(@PathVariable Long id) {
        return mailUserService.getMailUserById(id);
    }

    @PostMapping
    public AApiResponse<MailUserDTO> createMailUser(@RequestBody MailUserDTO mailUserDTO) {
        MailUserDTO createdMailUser = mailUserService.createMailUser(mailUserDTO);
        AApiResponse<MailUserDTO> response = new AApiResponse<>();
        response.setResponseCode(200);
        response.setData(Collections.singletonList(createdMailUser));
        response.setOffset(0);
        response.setMax(1);
        response.setMessage("Mail user created successfully");
        response.setLength(1);
        return response;
    }



    @DeleteMapping("/{id}")
    public void deleteMailUser(@PathVariable Long id) {
        mailUserService.deleteMailUser(id);
    }
}
