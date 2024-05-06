package com.alekseyruban.barbershopServer.service.impl;

import com.alekseyruban.barbershopServer.dto.AuthTokenDTO;
import com.alekseyruban.barbershopServer.entity.AuthorizationToken;
import com.alekseyruban.barbershopServer.helpers.EmailWorker;
import com.alekseyruban.barbershopServer.helpers.RandomGenerator;
import com.alekseyruban.barbershopServer.repository.AuthorizationTokenRepository;
import com.alekseyruban.barbershopServer.service.AuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthTokenServiceImpl implements AuthTokenService {

    private final AuthorizationTokenRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthTokenServiceImpl(AuthorizationTokenRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthorizationToken createToken(AuthTokenDTO tokenDTO) {
        String tokenValue = RandomGenerator.generateClientToken();
        AuthorizationToken token = AuthorizationToken.builder()
                .client(tokenDTO.getClient())
                .token(tokenValue)
                .build();
        token.setToken(passwordEncoder.encode(token.getToken()));

        tokenDTO.setToken(tokenValue);
        EmailWorker.sendConfirmEmail(tokenDTO);
        return repository.save(token);
    }

    @Override
    public AuthorizationToken getByClientId(Long clientId) {
        return repository.findByClientId(clientId);
    }

    @Override
    public void deleteToken(Long id) {
        repository.deleteById(id);
    }

    private AuthTokenDTO mapToDTO(AuthorizationToken authorizationToken) {
        return AuthTokenDTO.builder()
                .id(authorizationToken.getId())
                .client(authorizationToken.getClient())
                .token(authorizationToken.getToken())
                .creationTime(authorizationToken.getCreationTime())
                .build();
    }

}
