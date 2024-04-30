package com.itma.gestionProjet.controllers;


import com.itma.gestionProjet.entities.User;
import com.itma.gestionProjet.events.RegistrationCompleteEvent;
import com.itma.gestionProjet.requests.UserRequest;
import com.itma.gestionProjet.services.imp.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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

    private  final ApplicationEventPublisher publisher;

    public UserController(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping(path = "/createUser",method = RequestMethod.POST)
    public User createRole(@RequestBody UserRequest userRequest,final HttpServletRequest request) {
        User user =userService.saveUser(userRequest);
        publisher.publishEvent(new RegistrationCompleteEvent(user,applicationUrl(request)));
        return userService.saveUser(userRequest);
    }

    public String applicationUrl(HttpServletRequest httpServlet) {
        return "http://"+httpServlet.getServerName()+":"+httpServlet.getServerPort()+httpServlet.getContextPath();

    }

}
