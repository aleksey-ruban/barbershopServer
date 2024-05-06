package com.alekseyruban.barbershopServer.repository;

import com.alekseyruban.barbershopServer.entity.AuthorizationToken;
import com.alekseyruban.barbershopServer.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationTokenRepository extends JpaRepository<AuthorizationToken, Long> {
    AuthorizationToken findByToken(String token);

    @Query("SELECT t FROM AuthorizationToken t WHERE t.client.id = :clientid")
    AuthorizationToken findByClientId(@Param("clientid") Long clientid);
}
