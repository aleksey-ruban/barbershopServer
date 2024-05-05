package com.alekseyruban.barbershopServer.service.impl;

import com.alekseyruban.barbershopServer.dto.TreatmentServiceDTO;
import com.alekseyruban.barbershopServer.entity.TreatmentService;
import com.alekseyruban.barbershopServer.repository.TreatmentServiceRepository;
import com.alekseyruban.barbershopServer.service.TreatmentServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TreatmentServiceServiceImpl implements TreatmentServiceService {

    private final TreatmentServiceRepository repository;

    @Autowired
    public TreatmentServiceServiceImpl(TreatmentServiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public TreatmentService create(TreatmentServiceDTO treatmentServiceDTO) {
        TreatmentService treatmentService = TreatmentService.builder()
                .name(treatmentServiceDTO.getName())
                .coast(treatmentServiceDTO.getCoast())
                .duration(treatmentServiceDTO.getDuration())
                .build();

        return repository.save(treatmentService);
    }

    @Override
    public List<TreatmentService> readAll() {
        return repository.findAll();
    }

    @Override
    public List<TreatmentService> readAllById(List<Long> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public Long getId(String name) {
        Optional<TreatmentService> treatmentService = repository.findByName(name);
        return treatmentService.map(TreatmentService::getId).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
