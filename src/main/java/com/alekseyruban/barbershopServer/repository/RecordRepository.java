package com.alekseyruban.barbershopServer.repository;

import com.alekseyruban.barbershopServer.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

}
