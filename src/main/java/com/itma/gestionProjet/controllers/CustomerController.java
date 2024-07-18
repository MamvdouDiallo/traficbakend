package com.itma.gestionProjet.controllers;

import com.itma.gestionProjet.dtos.BaseResponse;
import com.itma.gestionProjet.entities.Customer;
import com.itma.gestionProjet.repositories.CustomerRepository;
import com.itma.gestionProjet.services.CustomerService;
import com.itma.gestionProjet.utils.CustomerDTO;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepository;

    private final CustomerService customerService;



    @PostMapping("/import")
    public ResponseEntity<BaseResponse> importCustomerData(@RequestParam("file") MultipartFile importFile) {
        BaseResponse baseResponse = customerService.importCustomerData(importFile);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<BaseResponse> createCustomer(@RequestBody CustomerDTO customerDTO){
        return new ResponseEntity<>(customerService.createCustomer(customerDTO), HttpStatus.OK);
    }


}
