package com.pitty.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteWriteDTO {
    @NotBlank
    @Size(max = 120)
    private String nombre;

    @Size(max = 30)
    private String telefono;

    @Size(max = 300)
    private String notas;

    @Size(max = 120)
    private String createdBy;

    @Size(max = 120)
    private String updatedBy;
}
