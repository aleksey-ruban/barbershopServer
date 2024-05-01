package com.alekseyruban.barbershopServer.repository;

import com.alekseyruban.barbershopServer.entity.Master;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterRepository extends JpaRepository<Master, Long> {

}
