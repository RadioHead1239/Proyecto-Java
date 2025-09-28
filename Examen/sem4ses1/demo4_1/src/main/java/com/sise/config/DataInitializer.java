package com.sise.config;

import com.sise.model.Cita;
import com.sise.model.Cliente;
import com.sise.model.DetalleVenta;
import com.sise.model.Mascota;
import com.sise.model.Pago;
import com.sise.model.Producto;
import com.sise.model.Servicio;
import com.sise.model.Usuario;
import com.sise.model.Venta;
import com.sise.service.CitaService;
import com.sise.service.ClienteService;
import com.sise.service.MascotaService;
import com.sise.service.PagoService;
import com.sise.service.ProductoService;
import com.sise.service.ServicioService;
import com.sise.service.UsuarioService;
import com.sise.service.VentaService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioService usuarioService;
    private final ClienteService clienteService;
    private final MascotaService mascotaService;
    private final ServicioService servicioService;
    private final ProductoService productoService;
    private final CitaService citaService;
    private final PagoService pagoService;
    private final VentaService ventaService;

    @Override
    public void run(String... args) {
        if (!usuarioService.findAll().isEmpty()) {
            return;
        }

        Usuario admin = Usuario.builder()
                .nombre("Administrador")
                .correo("admin@peluqueria.com")
                .password("admin123")
                .rol(Usuario.Rol.Administrador)
                .estado(true)
                .build();
        usuarioService.save(admin);

        Usuario recepcion = Usuario.builder()
                .nombre("Recepcion")
                .correo("recepcion@peluqueria.com")
                .password("recepcion123")
                .rol(Usuario.Rol.Recepcionista)
                .estado(true)
                .build();
        usuarioService.save(recepcion);

        Cliente cliente = Cliente.builder()
                .nombre("Juan")
                .apellido("Pérez")
                .correo("juan.perez@mail.com")
                .telefono("999999999")
                .direccion("Av. Las Flores 123")
                .build();
        cliente = clienteService.save(cliente);

        Mascota mascota = Mascota.builder()
                .nombre("Firulais")
                .cliente(cliente)
                .especie(Mascota.Especie.Perro)
                .raza("Labrador")
                .edad(3)
                .peso(12.5)
                .observaciones("Es muy sociable")
                .build();
        mascotaService.save(mascota);

        Servicio servicio = Servicio.builder()
                .nombre("Baño Premium")
                .descripcion("Incluye corte de uñas y limpieza de oídos")
                .precio(80.0)
                .duracionMinutos(60)
                .build();
        servicioService.save(servicio);

        Producto producto = Producto.builder()
                .nombre("Shampoo Hipoalergénico")
                .descripcion("Ideal para pieles sensibles")
                .precio(45.0)
                .stock(25)
                .build();
        productoService.save(producto);

        Cita cita = Cita.builder()
                .cliente(cliente)
                .mascota(mascota)
                .servicio(servicio)
                .usuario(admin)
                .fechaCita(LocalDateTime.now().plusDays(1))
                .estado(Cita.Estado.Confirmada)
                .observaciones("Cliente solicita fragancia suave")
                .build();
        cita = citaService.save(cita);

        Pago pago = Pago.builder()
                .cita(cita)
                .monto(80.0)
                .build();
        pagoService.save(pago);

        Venta venta = new Venta();
        venta.setCliente(cliente);
        DetalleVenta detalleVenta = DetalleVenta.builder()
                .producto(producto)
                .cantidad(2)
                .subtotal(producto.getPrecio() * 2)
                .build();
        venta.setDetalles(List.of(detalleVenta));
        venta.setTotal(BigDecimal.valueOf(detalleVenta.getSubtotal()));
        ventaService.save(venta);
    }
}
