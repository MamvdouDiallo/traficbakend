package com.itma.gestionProjet.controllers;

import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.PlainteUserDTO;
import com.itma.gestionProjet.entities.PlainteUser;
import com.itma.gestionProjet.services.PlainteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/complaints")
public class PlainteUserController {

    private final PlainteUserService plainteUserService;

    @Autowired
    public PlainteUserController(PlainteUserService plainteUserService) {
        this.plainteUserService = plainteUserService;
    }

    @PostMapping
    public ResponseEntity<AApiResponse<PlainteUserDTO>> createComplaint(@RequestBody PlainteUserDTO plainteUserDTO) {
        PlainteUser plainteUser = plainteUserService.createComplaint(plainteUserDTO);

        // Convertir l'entité en DTO
        PlainteUserDTO dto = new PlainteUserDTO();
        dto.setFirstName(plainteUser.getFirstName());
        dto.setLastName(plainteUser.getLastName());
        dto.setEmail(plainteUser.getEmail());
        dto.setPhone(plainteUser.getPhone());
        dto.setComplaintType(plainteUser.getComplaintType());
        dto.setComplaintDescription(plainteUser.getComplaintDescription());
        dto.setEtat(plainteUser.getEtat());
        dto.setCreatedAt(plainteUser.getCreatedAt());

        AApiResponse<PlainteUserDTO> response = new AApiResponse<>();
        response.setResponseCode(HttpStatus.CREATED.value());
        response.setData(List.of(dto));
        response.setMessage("Complaint created successfully");
        response.setLength(1);
        response.setOffset(0);
        response.setMax(1);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<AApiResponse<PlainteUserDTO>> getAllComplaints() {
        List<PlainteUser> complaints = plainteUserService.getAllComplaints();
        List<PlainteUserDTO> plainteUserDTOList = complaints.stream().map(complaint -> {
            PlainteUserDTO dto = new PlainteUserDTO();
            dto.setFirstName(complaint.getFirstName());
            dto.setLastName(complaint.getLastName());
            dto.setEmail(complaint.getEmail());
            dto.setPhone(complaint.getPhone());
            dto.setComplaintType(complaint.getComplaintType());
            dto.setComplaintDescription(complaint.getComplaintDescription());
            dto.setEtat(complaint.getEtat());
            dto.setCreatedAt(complaint.getCreatedAt());
            return dto;
        }).collect(Collectors.toList());

        AApiResponse<PlainteUserDTO> response = new AApiResponse<>();
        response.setResponseCode(HttpStatus.OK.value());
        response.setData(plainteUserDTOList);
        response.setMessage("Complaints fetched successfully");
        response.setLength(plainteUserDTOList.size());
        response.setOffset(0);
        response.setMax(plainteUserDTOList.size());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
