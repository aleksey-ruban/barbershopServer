package com.alekseyruban.barbershopServer.repository;

import com.alekseyruban.barbershopServer.entity.TreatmentService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TreatmentServiceRepository extends JpaRepository<TreatmentService, Long> {
    Optional<TreatmentService> findByName(String name);
}
