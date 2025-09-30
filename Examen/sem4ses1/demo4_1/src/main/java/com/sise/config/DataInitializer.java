package com.sise.config;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sise.model.Cita;
import com.sise.model.Cliente;
import com.sise.model.DetalleVenta;
import com.sise.model.Mascota;
import com.sise.model.Producto;
import com.sise.model.Servicio;
import com.sise.model.Usuario;
import com.sise.model.Venta;
import com.sise.repository.CitaRepository;
import com.sise.repository.ClienteRepository;
import com.sise.repository.DetalleVentaRepository;
import com.sise.repository.MascotaRepository;
import com.sise.repository.ProductoRepository;
import com.sise.repository.ServicioRepository;
import com.sise.repository.UsuarioRepository;
import com.sise.repository.VentaRepository;
import com.sise.service.VentaService;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private MascotaRepository mascotaRepository;
    
    @Autowired
    private ServicioRepository servicioRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private CitaRepository citaRepository;
    
    @Autowired
    private VentaRepository ventaRepository;
    
    
    @Autowired
    private DetalleVentaRepository detalleVentaRepository;
    
    @Autowired
    private VentaService ventaService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            System.out.println("Iniciando creación de datos iniciales...");
            createInitialData();
            System.out.println("Datos iniciales creados exitosamente!");
        } else {
            System.out.println("Los datos iniciales ya existen. Saltando creación...");
        }
    }

    private void createInitialData() {
        try {
            System.out.println("Creando usuarios...");
            Usuario admin = createUsuario("Administrador", "admin@peluqueria.com", "admin123", Usuario.Rol.Administrador);
            createUsuario("María González", "maria@peluqueria.com", "maria123", Usuario.Rol.Recepcionista);
            Usuario peluquero = createUsuario("Carlos López", "carlos@peluqueria.com", "carlos123", Usuario.Rol.Peluquero);

            System.out.println("Creando servicios...");
            Servicio servicio1 = createServicio("Consulta General", "Examen físico completo y evaluación de salud general", 
                new BigDecimal("50.00"), 60, "Consulta General");
            createServicio("Desparasitación", "Administración de antiparasitarios internos y externos", 
                new BigDecimal("35.00"), 30, "Desparasitación");
            Servicio servicio3 = createServicio("Vacunación", "Aplicación de vacunas según calendario veterinario", 
                new BigDecimal("40.00"), 20, "Vacunación");

            System.out.println("Creando productos...");
            Producto producto1 = createProducto("Amoxicilina 250mg", "Antibiótico para tratamiento de infecciones bacterianas", 
                new BigDecimal("45.00"), 50, 10, "Antibióticos", "VetPharma");
            createProducto("Vacuna Triple Felina", "Vacuna contra panleucopenia, calicivirus y rinotraqueitis", 
                new BigDecimal("35.00"), 30, 5, "Vacunas", "Zoetis");
            createProducto("Shampoo Antialérgico", "Shampoo hipoalergénico para mascotas con piel sensible", 
                new BigDecimal("25.00"), 20, 5, "Higiene", "VetCare");

                   System.out.println("👥 Creando clientes...");
                   createCliente("Público en", "General", "000000000", "publico@vetcare.com", "Cliente público para ventas sin registro");
                   Cliente cliente1 = createCliente("Ana", "García", "987654321", "ana.garcia@email.com", "Av. Principal 123, Lima");
                   Cliente cliente2 = createCliente("Juan", "Pérez", "987654322", "juan.perez@email.com", "Jr. Secundaria 456, Lima");
                   createCliente("María", "López", "987654323", "maria.lopez@email.com", "Av. Norte 789, Lima");

            System.out.println(" Creando mascotas...");
            Mascota mascota1 = createMascota("Max", Mascota.Especie.Perro, "Labrador", 3, new BigDecimal("25.5"), 
                "Muy amigable y juguetón", cliente1);
            Mascota mascota2 = createMascota("Luna", Mascota.Especie.Gato, "Siamés", 2, new BigDecimal("4.2"), 
                "Tímida pero cariñosa", cliente2);
            createMascota("Rocky", Mascota.Especie.Perro, "Pastor Alemán", 5, new BigDecimal("35.0"), 
                "Protector y leal", cliente1);

            System.out.println(" Creando citas de ejemplo...");
            LocalDateTime fechaCita1 = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0);
            createCita(fechaCita1, Cita.Estado.Pendiente, "Primera cita para Max", 
                cliente1, mascota1, servicio1, peluquero);

            LocalDateTime fechaCita2 = LocalDateTime.now().plusDays(2).withHour(14).withMinute(30);
            createCita(fechaCita2, Cita.Estado.Confirmada, "Vacunación anual", 
                cliente2, mascota2, servicio3, admin);

            System.out.println(" Creando citas completadas (generarán ventas automáticamente)...");
            LocalDateTime fechaCitaCompletada1 = LocalDateTime.now().minusDays(1).withHour(9).withMinute(0);
            Cita citaCompletada1 = createCita(fechaCitaCompletada1, Cita.Estado.Completada, "Consulta completada exitosamente", 
                cliente1, mascota1, servicio1, admin);

            LocalDateTime fechaCitaCompletada2 = LocalDateTime.now().minusHours(2).withMinute(0);
            Cita citaCompletada2 = createCita(fechaCitaCompletada2, Cita.Estado.Completada, "Vacunación completada", 
                cliente2, mascota2, servicio3, peluquero);

            System.out.println(" Creando ventas generadas por citas...");
            ventaService.crearVentaDesdeCita(citaCompletada1);
            ventaService.crearVentaDesdeCita(citaCompletada2);

            System.out.println("🛒 Creando venta regular de productos...");
            Venta ventaRegular = createVenta(cliente1, admin, new BigDecimal("45.00"), Venta.Estado.Completada, "Venta de medicamentos");
            
            createDetalleVenta(ventaRegular, producto1, 1, producto1.getPrecio());

            System.out.println(" ¡Datos iniciales creados exitosamente!");
            System.out.println(" Usuarios: admin@peluqueria.com (admin123), maria@peluqueria.com (maria123), carlos@peluqueria.com (carlos123)");
            System.out.println(" Mascotas: Max (Labrador), Luna (Siamés), Rocky (Pastor Alemán)");
            System.out.println(" Servicios: Consulta General, Desparasitación, Vacunación");
            System.out.println(" Productos: Amoxicilina, Vacuna Triple Felina, Shampoo Antialérgico");
            System.out.println(" Citas: 4 citas creadas (2 pendientes, 2 completadas)");
            System.out.println(" Ventas: 3 ventas creadas (2 generadas por citas, 1 regular)");
            System.out.println(" Las citas completadas generan ventas automáticamente con el precio del servicio");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Usuario createUsuario(String nombre, String correo, String password, Usuario.Rol rol) {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setRol(rol);
        usuario.setEstado(true);
        usuario.setImagen("https://avatars.githubusercontent.com/u/9011267?v=4" + (rol.ordinal() + 1));
        return usuarioRepository.save(usuario);
    }

    private Cliente createCliente(String nombre, String apellido, String telefono, String correo, String direccion) {
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setTelefono(telefono);
        cliente.setCorreo(correo);
        cliente.setDireccion(direccion);
        cliente.setImagen("https://cdn-icons-png.freepik.com/512/4143/4143131.png" + (clienteRepository.count() + 4));
        return clienteRepository.save(cliente);
    }

    private Mascota createMascota(String nombre, Mascota.Especie especie, String raza, Integer edad, 
                                 BigDecimal peso, String observaciones, Cliente cliente) {
        Mascota mascota = new Mascota();
        mascota.setNombre(nombre);
        mascota.setEspecie(especie);
        mascota.setRaza(raza);
        mascota.setEdad(edad);
        mascota.setPeso(peso);
        mascota.setObservaciones(observaciones);
        mascota.setImagen("https://www.respetmascotas.com/_Assets/img/181129-Imagen-AlimentacionMascotas.jpg");
        mascota.setCliente(cliente);
        return mascotaRepository.save(mascota);
    }

    private Servicio createServicio(String nombre, String descripcion, BigDecimal precio, 
                                  Integer duracionMinutos, String categoria) {
        Servicio servicio = new Servicio();
        servicio.setNombre(nombre);
        servicio.setDescripcion(descripcion);
        servicio.setPrecio(precio);
        servicio.setDuracionMinutos(duracionMinutos);
        servicio.setCategoria(categoria);
        servicio.setActivo(true);
        servicio.setImagen("https://media.istockphoto.com/id/1456882550/es/vector/hotel-para-animales-dom%C3%A9sticos-refugio-para-mascotas-servicio-veterinario-hogar-con-perro-o.jpg?s=612x612&w=0&k=20&c=5PLN1h7EPUKEEBpzu3CQakrjCJ9XkEtp05r2xhAy1_s=");
        return servicioRepository.save(servicio);
    }

    private Producto createProducto(String nombre, String descripcion, BigDecimal precio, 
                                  Integer stock, Integer stockMinimo, String categoria, String marca) {
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setStockMinimo(stockMinimo);
        producto.setCategoria(categoria);
        producto.setMarca(marca);
        producto.setActivo(true);
        producto.setImagen("https://images.unsplash.com/photo-1601758123927-1975a5e2b94c?w=300&h=200&fit=crop");
        return productoRepository.save(producto);
    }

    private Cita createCita(LocalDateTime fechaCita, Cita.Estado estado, String observaciones,
                          Cliente cliente, Mascota mascota, Servicio servicio, Usuario usuario) {
        Cita cita = new Cita();
        cita.setFechaCita(fechaCita);
        cita.setEstado(estado);
        cita.setObservaciones(observaciones);
        cita.setCliente(cliente);
        cita.setMascota(mascota);
        cita.setServicio(servicio);
        cita.setUsuario(usuario);
        return citaRepository.save(cita);
    }

    private Venta createVenta(Cliente cliente, Usuario usuario, BigDecimal total, 
                            Venta.Estado estado, String observaciones) {
        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setUsuario(usuario);
        venta.setTotal(total);
        venta.setEstado(estado);
        venta.setObservaciones(observaciones);
        return ventaRepository.save(venta);
    }

    private DetalleVenta createDetalleVenta(Venta venta, Producto producto, Integer cantidad, BigDecimal precioUnitario) {
        DetalleVenta detalle = new DetalleVenta();
        detalle.setVenta(venta);
        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);
        detalle.setPrecioUnitario(precioUnitario);
        detalle.setSubtotal(precioUnitario.multiply(BigDecimal.valueOf(cantidad)));
        return detalleVentaRepository.save(detalle);
    }

}
