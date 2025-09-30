package com.sise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private Integer stockMinimo;
    private String imagen;
    private String categoria;
    private String marca;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
    private Integer totalVendido;
}
