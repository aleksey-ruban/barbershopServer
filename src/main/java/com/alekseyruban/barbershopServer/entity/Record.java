package com.alekseyruban.barbershopServer.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@IdClass(RecordKey.class)
public class Record {
    @Id
    private Long recordId;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "master_id", referencedColumnName = "id")
    private Master master;

    @ManyToMany
    private Set<TreatmentService> services;

    @Column(name = "date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime dateTime;

    private Boolean isDone;
}
