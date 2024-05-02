package com.alekseyruban.barbershopServer.dto;

import com.alekseyruban.barbershopServer.entity.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokenDTO {
    private Long id;
    private Client client;
    private String token;
    private Instant creationTime;
}
