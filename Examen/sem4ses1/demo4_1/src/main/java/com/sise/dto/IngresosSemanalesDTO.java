package com.sise.dto;

import java.time.LocalDate;

public class IngresosSemanalesDTO {
    private LocalDate fecha;
    private Double total;
    
    public IngresosSemanalesDTO() {}
    
    public IngresosSemanalesDTO(LocalDate fecha, Double total) {
        this.fecha = fecha;
        this.total = total;
    }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
}
