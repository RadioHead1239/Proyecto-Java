package com.sise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String correo;
    private String password;
    private String rol;
    private String imagen;
    private Boolean estado;
    private LocalDateTime fechaCreacion;
    private Integer totalCitas;
    private Integer totalVentas;
}