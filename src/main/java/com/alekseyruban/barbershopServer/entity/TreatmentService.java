package com.alekseyruban.barbershopServer.entity;

import jakarta.persistence.*;

@Entity
public class TreatmentService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private Integer duration;
}
