package com.alekseyruban.barbershopServer.service;

import com.alekseyruban.barbershopServer.dto.TreatmentServiceDTO;
import com.alekseyruban.barbershopServer.entity.TreatmentService;

import java.util.List;

public interface TreatmentServiceService {
    TreatmentService create(TreatmentServiceDTO treatmentServiceDTO);
    List<TreatmentService> readAll();
    List<TreatmentService> readAllById(List<Long> ids);
    Long getId(String name);
    void delete(Long id);
}
