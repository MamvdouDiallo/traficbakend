package com.itma.gestionProjet.controllers;


import com.itma.gestionProjet.dtos.UserDTO;
import com.itma.gestionProjet.requests.UserRequest;
import com.itma.gestionProjet.services.imp.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping(path = "/createUser",method = RequestMethod.POST)
    public UserDTO createRole(@RequestBody UserRequest roleRequest) {
        return userService.saveUser(roleRequest);
    }

}
