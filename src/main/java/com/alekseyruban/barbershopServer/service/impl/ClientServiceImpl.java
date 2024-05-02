package com.alekseyruban.barbershopServer.service.impl;

import com.alekseyruban.barbershopServer.dto.ClientDTO;
import com.alekseyruban.barbershopServer.entity.Client;
import com.alekseyruban.barbershopServer.helpers.EmailWorker;
import com.alekseyruban.barbershopServer.helpers.RandomGenerator;
import com.alekseyruban.barbershopServer.repository.ClientRepository;
import com.alekseyruban.barbershopServer.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;
//    private PasswordEncoder passwordEncoder;

    @Autowired
    public ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Client create(ClientDTO clientDTO) {

        Client client = Client.builder()
                .email(clientDTO.getEmail())
                .password(clientDTO.getPassword())
                .name(clientDTO.getName())
                .isConfirmed(false)
                .roles("CLIENT")
                .build();
//        client.setPassword(passwordEncoder.encode(client.getPassword()));

        Client savedClient = repository.save(client);

        return savedClient;
    }

    @Override
    public Client confirmEmail(Client client) {
        client.setIsConfirmed(true);
        client.setToken(null);
        return repository.save(client);
    }

    @Override
    public Client restorePassword(String email) {

        Optional<Client> clientOptional = repository.findByEmail(email);

        if (clientOptional.isPresent()) {
            String password = RandomGenerator.randomPassword(16);

            Client client = clientOptional.get();
            client.setPassword(password);
//            client.setPassword(passwordEncoder.encode(client.getPassword()));

            Client savedClient = repository.save(client);

            EmailWorker.passwordRestored(client.getEmail(), password);

            return savedClient;
        }

        return null;
    }

    private Client mapToClient(ClientDTO dto) {
        return Client.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .isConfirmed(dto.getIsConfirmed())
                .roles(dto.getRoles())
                .build();
    }
}
