package com.sise.dto;

import java.math.BigDecimal;
import java.util.List;

public class DashboardDTO {
    private long totalClientes;
    private long totalMascotas;
    private long citasHoy;
    private BigDecimal ingresosHoy;

    private List<CitaDTO> proximasCitas;
    private List<VentaDTO> ultimasVentas;

    private List<VentasSemanaDTO> ventasSemanales;
    private List<MascotaDistribucionDTO> distribucionMascotas;

    public long getTotalClientes() { return totalClientes; }
    public void setTotalClientes(long totalClientes) { this.totalClientes = totalClientes; }

    public long getTotalMascotas() { return totalMascotas; }
    public void setTotalMascotas(long totalMascotas) { this.totalMascotas = totalMascotas; }

    public long getCitasHoy() { return citasHoy; }
    public void setCitasHoy(long citasHoy) { this.citasHoy = citasHoy; }

    public BigDecimal getIngresosHoy() { return ingresosHoy; }
    public void setIngresosHoy(BigDecimal ingresosHoy) { this.ingresosHoy = ingresosHoy; }

    public List<CitaDTO> getProximasCitas() { return proximasCitas; }
    public void setProximasCitas(List<CitaDTO> proximasCitas) { this.proximasCitas = proximasCitas; }

    public List<VentaDTO> getUltimasVentas() { return ultimasVentas; }
    public void setUltimasVentas(List<VentaDTO> ultimasVentas) { this.ultimasVentas = ultimasVentas; }

    public List<VentasSemanaDTO> getVentasSemanales() { return ventasSemanales; }
    public void setVentasSemanales(List<VentasSemanaDTO> ventasSemanales) { this.ventasSemanales = ventasSemanales; }

    public List<MascotaDistribucionDTO> getDistribucionMascotas() { return distribucionMascotas; }
    public void setDistribucionMascotas(List<MascotaDistribucionDTO> distribucionMascotas) { this.distribucionMascotas = distribucionMascotas; }

}
