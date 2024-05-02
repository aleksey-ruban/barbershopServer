package com.alekseyruban.barbershopServer.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_id", referencedColumnName = "id")
    private Master master;

    @ManyToMany
    private Set<TreatmentService> services;

    @Column(name = "date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime dateTime;

    private Boolean isDone;
}
