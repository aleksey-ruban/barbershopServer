package com.alekseyruban.barbershopServer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentServiceDTO {
    private Long id;
    private String name;
    private Integer coast;
    private Integer duration;
}
