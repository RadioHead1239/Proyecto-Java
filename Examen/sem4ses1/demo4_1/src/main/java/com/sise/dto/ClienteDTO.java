package com.sise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String direccion;
    private String imagen;
    private LocalDateTime fechaRegistro;
    private Integer totalMascotas;
    private Integer totalCitas;
}