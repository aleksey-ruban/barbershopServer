package com.alekseyruban.barbershopServer.service;

import com.alekseyruban.barbershopServer.dto.AuthTokenDTO;
import com.alekseyruban.barbershopServer.entity.AuthorizationToken;

public interface AuthTokenService {
    AuthorizationToken createToken(AuthTokenDTO tokenDTO);
    AuthorizationToken getByClientId(Long clientId);
    void deleteToken(Long id);
}
