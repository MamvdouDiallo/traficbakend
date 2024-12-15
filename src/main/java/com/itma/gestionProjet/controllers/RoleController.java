package com.itma.gestionProjet.controllers;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.RoleDTO;
import com.itma.gestionProjet.entities.Role;
import com.itma.gestionProjet.requests.RoleRequest;
import com.itma.gestionProjet.services.imp.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    public AApiResponse<Role> getRoles() {
        List<Role> roles = roleService.getAllRoles();
        return new AApiResponse<>(200, roles, 0, roles.size(), "Roles retrieved successfully", roles.size());
    }

    @RequestMapping(path = "/createRole", method = RequestMethod.POST)
    public AApiResponse<RoleDTO> createRole(@RequestBody RoleRequest roleRequest) {
        RoleDTO createdRole = roleService.saveRole(roleRequest);
        return new AApiResponse<>(201, List.of(createdRole), 0, 1, "Role created successfully", 1);
    }

    @RequestMapping(path = "/updateRole/{id}", method = RequestMethod.PUT)
    public AApiResponse<RoleDTO> updateRole(@RequestBody RoleRequest roleRequest,@PathVariable Long id) {
        RoleDTO updatedRole = roleService.updateRole(id,roleRequest);
        return new AApiResponse<>(200, List.of(updatedRole), 0, 1, "Role updated successfully", 1);
    }
    @RequestMapping(path = "/deleteRole/{id}", method = RequestMethod.DELETE)
    public AApiResponse<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRoleById(id);
        return new AApiResponse<>(200, null, 0, 1, "Role deleted successfully", 1);
    }



}
