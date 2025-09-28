package com.sise.mapper;

import java.math.BigDecimal;

import com.sise.dto.VentaDTO;
import com.sise.dto.VentasSemanaDTO;
import com.sise.model.Venta;

public class VentaMapper {

    public static VentaDTO toDTO(Venta v) {
        VentaDTO dto = new VentaDTO();
        dto.setId(v.getIdVenta());
        dto.setCliente(v.getCliente().getNombre() + " " + v.getCliente().getApellido());
        dto.setFecha(v.getFecha());
        dto.setTotal(v.getTotal() != null ? v.getTotal() : BigDecimal.ZERO);
        return dto;
    }

    public static VentasSemanaDTO toSemanaDTO(Object[] row) {
        VentasSemanaDTO dto = new VentasSemanaDTO();
        dto.setSemana((String) row[0]);
        dto.setTotal(row[1] != null ? (BigDecimal) row[1] : BigDecimal.ZERO);
        return dto;
    }
}
