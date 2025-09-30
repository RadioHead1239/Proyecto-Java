package com.sise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaDTO {
    private Long id;
    private String nombre;
    private String especie;
    private String raza;
    private Integer edad;
    private BigDecimal peso;
    private String observaciones;
    private String imagen;
    private Long idCliente;
    private String cliente; // Nombre del cliente para mostrar
    private LocalDateTime fechaRegistro;
}