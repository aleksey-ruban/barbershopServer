package com.alekseyruban.barbershopServer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordKey implements Serializable {
    private Long recordId;
    private Client client;
}
