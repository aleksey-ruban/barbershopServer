package com.alekseyruban.barbershopServer.service;

import com.alekseyruban.barbershopServer.dto.MasterDTO;
import com.alekseyruban.barbershopServer.entity.Master;

import java.util.List;

public interface MasterService {
    Master create(MasterDTO masterDTO);
    List<Master> readAll();
    Long getId(String name);
    Master getById(Long id);
    void delete(Long id);
}
