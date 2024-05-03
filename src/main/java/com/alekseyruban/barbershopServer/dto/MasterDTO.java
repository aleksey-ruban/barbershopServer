package com.alekseyruban.barbershopServer.dto;

import com.alekseyruban.barbershopServer.enums.MasterQualification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MasterDTO {
    private Long id;
    private String name;
    private MasterQualification qualification;
}
