package com.alekseyruban.barbershopServer.entity;

import jakarta.persistence.*;

@Entity
public class ClientService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer duration;


}
