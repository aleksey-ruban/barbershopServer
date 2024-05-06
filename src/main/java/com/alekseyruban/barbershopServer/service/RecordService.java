package com.alekseyruban.barbershopServer.service;

import com.alekseyruban.barbershopServer.dto.RecordDTO;
import com.alekseyruban.barbershopServer.entity.Record;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface RecordService {
    Record create(RecordDTO recordDTO);
    List<Record> readInRangeForMaster(Long masterId, LocalDate start, LocalDate end);
    List<Record> readByClientIdAndIsDone(Long clientId, Boolean isDone);
    List<Record> readByClientId(Long clientdId);
    Long getId(String clientEmail, String masterName, LocalDate date, LocalTime time);
    Record close(Long id);
    void delete(Long id);
}
