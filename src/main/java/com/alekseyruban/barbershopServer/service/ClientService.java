package com.alekseyruban.barbershopServer.service;

import com.alekseyruban.barbershopServer.entity.Client;
import com.alekseyruban.barbershopServer.dto.ClientDTO;

public interface ClientService {
    Client create(ClientDTO clientDTO);
    Client confirmEmail(Client client);
    Client getById(Long id);
    Client restorePassword(String email);
}
