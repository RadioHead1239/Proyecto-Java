package com.sise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaDTO {
    private Long id;
    private LocalDateTime fechaCita;
    private String estado;
    private String observaciones;
    private Long idCliente;
    private String cliente;
    private Long idMascota;
    private String mascota;
    private Long idServicio;
    private String servicio;
    private Long idUsuario;
    private String usuario;
    private BigDecimal precioServicio;
    private Long idVenta;
    private String venta;
    private Boolean tieneVenta;
}