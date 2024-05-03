package com.alekseyruban.barbershopServer.repository;

import com.alekseyruban.barbershopServer.entity.Master;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MasterRepository extends JpaRepository<Master, Long> {
    Optional<Master> findByName(String name);
}
