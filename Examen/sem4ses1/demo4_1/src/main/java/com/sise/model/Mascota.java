package com.sise.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "mascota")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mascota {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mascota")
    private Long id;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "especie", nullable = false)
    private Especie especie;
    
    @Column(name = "raza", length = 100)
    private String raza;
    
    @Column(name = "edad")
    private Integer edad;
    
    @Column(name = "peso", precision = 5, scale = 2)
    private BigDecimal peso;
    
    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
    
    @Column(name = "imagen", length = 255)
    private String imagen;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;
    
    @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cita> citas;
    
    public enum Especie {
        Perro, Gato, Otro
    }
}