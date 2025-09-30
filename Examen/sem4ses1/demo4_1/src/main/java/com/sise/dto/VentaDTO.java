package com.sise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaDTO {
    private Long id;
    private LocalDateTime fecha;
    private BigDecimal total;
    private String estado;
    private String observaciones;
    private Long idCliente;
    private String cliente;
    private Long idUsuario;
    private String usuario;
    private Long idCita;
    private String cita;
    private String servicio;
    private String mascota;
    private List<DetalleVentaDTO> detalles;
}