package com.sise.dto;

import java.time.LocalDate;

public class ClienteDTO {
    private int id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private LocalDate  fechaRegistro;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDate  getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate  fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

}
