package com.alekseyruban.barbershopServer.entity;

import com.alekseyruban.barbershopServer.enums.MasterQualification;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Master {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private MasterQualification qualification;

    @OneToMany(mappedBy = "master", cascade = CascadeType.ALL)
    private Set<Record> records;
}
