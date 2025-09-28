package com.sise.dto;

import java.math.BigDecimal;

public class VentasSemanaDTO {
    private String semana;       
    private BigDecimal total;    

    public VentasSemanaDTO() {}
    public VentasSemanaDTO(String semana, BigDecimal total) {
        this.semana = semana;
        this.total = total;
    }

    public String getSemana() { return semana; }
    public void setSemana(String semana) { this.semana = semana; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
}
