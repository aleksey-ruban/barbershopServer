package com.alekseyruban.barbershopServer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
    private Long id;
    private String email;
    private String password;
    private String name;
    private Boolean isConfirmed;
    private String roles;
}
