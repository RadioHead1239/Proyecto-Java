package com.sise.dto;

public class MascotaDistribucionDTO {
    private String especie;
    private long cantidad;    

    public MascotaDistribucionDTO() {}
    public MascotaDistribucionDTO(String especie, long cantidad) {
        this.especie = especie;
        this.cantidad = cantidad;
    }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public long getCantidad() { return cantidad; }
    public void setCantidad(long cantidad) { this.cantidad = cantidad; }
}
