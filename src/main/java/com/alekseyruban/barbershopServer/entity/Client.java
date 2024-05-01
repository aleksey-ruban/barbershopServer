package com.alekseyruban.barbershopServer.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    private Boolean isConfirmed;

    @OneToOne(mappedBy = "client")
    private AuthorizationToken token;

    @OneToMany(mappedBy = "client")
    private Set<Record> records;

    private String roles;
}
