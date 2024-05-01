package com.alekseyruban.barbershopServer.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@IdClass(AuthTokenKey.class)
public class AuthorizationToken {
    @Id
    private Long tokenId;

    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    private String token;

    @CreationTimestamp
    private Instant creationTime;
}
