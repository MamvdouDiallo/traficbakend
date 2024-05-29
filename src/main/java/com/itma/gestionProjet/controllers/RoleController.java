package com.itma.gestionProjet.controllers;

import com.itma.gestionProjet.dtos.RoleDTO;
import com.itma.gestionProjet.entities.Role;
import com.itma.gestionProjet.requests.RoleRequest;
import com.itma.gestionProjet.services.imp.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    RoleService roleService;
    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<Role> getRoles() {
        return roleService.getAllRoles();
    }

    private boolean checkUserAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    @RequestMapping(path = "/createRole",method = RequestMethod.POST)
    public RoleDTO createRole(@RequestBody RoleRequest roleRequest) {
        return roleService.saveRole(roleRequest);
    }




}
