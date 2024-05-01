package com.alekseyruban.barbershopServer.repository;

import com.alekseyruban.barbershopServer.entity.AuthTokenKey;
import com.alekseyruban.barbershopServer.entity.AuthorizationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationTokenRepository extends JpaRepository<AuthorizationToken, AuthTokenKey> {

}
