package com.sise.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pago")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long id;
    
    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;
    
    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false)
    private MetodoPago metodoPago;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado = Estado.Pendiente;
    
    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cita", nullable = false)
    private Cita cita;
    
    @PrePersist
    protected void onCreate() {
        fechaPago = LocalDateTime.now();
    }
    
    public enum MetodoPago {
        Efectivo, Tarjeta, Transferencia, Yape, Plin
    }
    
    public enum Estado {
        Pendiente, Completado, Cancelado, Reembolsado
    }
}