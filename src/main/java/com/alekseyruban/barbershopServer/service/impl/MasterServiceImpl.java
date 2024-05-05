package com.alekseyruban.barbershopServer.service.impl;

import com.alekseyruban.barbershopServer.dto.MasterDTO;
import com.alekseyruban.barbershopServer.entity.Master;
import com.alekseyruban.barbershopServer.repository.MasterRepository;
import com.alekseyruban.barbershopServer.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MasterServiceImpl implements MasterService {

    private MasterRepository repository;

    @Autowired
    public MasterServiceImpl(MasterRepository repository) {
        this.repository = repository;
    }

    @Override
    public Master create(MasterDTO masterDTO) {
        Master master = Master.builder()
                .name(masterDTO.getName())
                .qualification(masterDTO.getQualification())
                .build();

        return repository.save(master);
    }

    @Override
    public List<Master> readAll() {
        return repository.findAll();
    }

    @Override
    public Long getId(String name) {
        Optional<Master> master = repository.findByName(name);
        return master.map(Master::getId).orElse(null);
    }

    @Override
    public Master getById(Long id) {
        Optional<Master> master = repository.findById(id);
        return master.orElse(null);
    }


    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
