package com.itma.gestionProjet.services;


import com.itma.gestionProjet.dtos.AApiResponse;
import com.itma.gestionProjet.dtos.EmployePapDTO;
import com.itma.gestionProjet.entities.EmployePap;
import com.itma.gestionProjet.requests.EmployePapRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployePapService {
    AApiResponse<EmployePap> getAllEmployePaps(Pageable pageable);
    List<EmployePap> createEmployePap(List<EmployePapRequest> employePap);
    EmployePap updateEmployePap(Long id, EmployePap employePap);
    void deleteEmployePap(Long id);

    AApiResponse<List<EmployePap>> getEmployesByCodePap(String codePap, int offset, int max);
}
