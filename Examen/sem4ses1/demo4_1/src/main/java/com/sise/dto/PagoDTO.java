package com.sise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {
    private Long id;
    private BigDecimal monto;
    private LocalDateTime fechaPago;
    private String metodoPago;
    private String estado;
    private String observaciones;
    private Long idCita;
    private String cliente;
    private String servicio;
}
