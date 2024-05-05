package com.alekseyruban.barbershopServer.repository;

import com.alekseyruban.barbershopServer.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    @Query("SELECT r FROM Record r WHERE r.date BETWEEN :startDate AND :endDate AND r.master.id = :masterId")
    List<Record> findByDateRangeForMaster(@Param("masterId") Long masterId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT r FROM Record r WHERE r.master.name = :masterName AND r.date = :date AND r.time = :time")
    Record findByMasterNameAndDatetime(@Param("masterName") String masterName, @Param("date") LocalDate date, @Param("time") LocalTime time);

    @Query("SELECT r FROM Record r WHERE r.client.email = :email AND r.date = :date AND r.time = :time")
    Record findByEmailAndDatetime(@Param("email") String email, @Param("date") LocalDate date, @Param("time") LocalTime time);

    List<Record> findByClientIdAndIsDone(Long clientId, Boolean isDone);
}
