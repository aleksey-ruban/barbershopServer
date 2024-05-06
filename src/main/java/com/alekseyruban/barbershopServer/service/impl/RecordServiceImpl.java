package com.alekseyruban.barbershopServer.service.impl;

import com.alekseyruban.barbershopServer.dto.RecordDTO;
import com.alekseyruban.barbershopServer.entity.Record;
import com.alekseyruban.barbershopServer.repository.RecordRepository;
import com.alekseyruban.barbershopServer.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecordServiceImpl implements RecordService {

    private final RecordRepository repository;

    @Autowired
    public RecordServiceImpl(RecordRepository repository) {
        this.repository = repository;
    }

    @Override
    public Record create(RecordDTO recordDTO) {
        Record record = Record.builder()
                .client(recordDTO.getClient())
                .master(recordDTO.getMaster())
                .services(recordDTO.getServices())
                .date(recordDTO.getDate())
                .time(recordDTO.getTime())
                .isDone(false)
                .build();
            repository.save(record);
        return null;
    }

    @Override
    public List<Record> readInRangeForMaster(Long masterId, LocalDate start, LocalDate end) {
        return repository.findByDateRangeForMaster(masterId, start, end);
    }

    @Override
    public List<Record> readByClientIdAndIsDone(Long clientId, Boolean isDone) {
        return repository.findByClientIdAndIsDone(clientId, isDone);
    }

    @Override
    public List<Record> readByClientId(Long clientId) {
        return repository.findByClientId(clientId);
    }

    @Override
    public Long getId(String clientEmail, String masterName, LocalDate date, LocalTime time) {
        if (clientEmail == null && masterName == null) {
            return null;
        }
        Record record;
        if (clientEmail != null && !clientEmail.equals("")) {
            record = repository.findByEmailAndDatetime(clientEmail, date, time);
        } else {
            record = repository.findByMasterNameAndDatetime(masterName, date, time);
        }
        return record == null ? null : record.getId();
    }

    @Override
    public Record close(Long id) {
        Optional<Record> record = repository.findById(id);
        if (record.isPresent()) {
            Record newRecord = record.get();
            newRecord.setIsDone(true);
            return repository.save(newRecord);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
