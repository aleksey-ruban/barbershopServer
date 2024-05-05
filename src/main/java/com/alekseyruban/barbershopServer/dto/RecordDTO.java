package com.alekseyruban.barbershopServer.dto;

import com.alekseyruban.barbershopServer.entity.Client;
import com.alekseyruban.barbershopServer.entity.Master;
import com.alekseyruban.barbershopServer.entity.TreatmentService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecordDTO {
    private Long id;
    private Client client;
    private Master master;
    private List<TreatmentService> services;
    private LocalDate date;
    private LocalTime time;
    private Boolean isDone;
}
