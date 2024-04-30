package com.itma.gestionProjet.controllers;

import com.itma.gestionProjet.dtos.RoleDTO;
import com.itma.gestionProjet.entities.Role;
import com.itma.gestionProjet.requests.RoleRequest;
import com.itma.gestionProjet.services.imp.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


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

    @RequestMapping(path = "/createRole",method = RequestMethod.POST)
    public RoleDTO createRole(@RequestBody RoleRequest roleRequest) {
        return roleService.saveRole(roleRequest);
    }


}
