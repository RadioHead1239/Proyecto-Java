package com.sise.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long id;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
    
    @Column(name = "stock", nullable = false)
    private Integer stock = 0;
    
    @Column(name = "stock_minimo")
    private Integer stockMinimo = 0;
    
    @Column(name = "imagen", length = 255)
    private String imagen;
    
    @Column(name = "categoria", length = 50)
    private String categoria;
    
    @Column(name = "marca", length = 50)
    private String marca;
    
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleVenta> detalleVentas;
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }
}