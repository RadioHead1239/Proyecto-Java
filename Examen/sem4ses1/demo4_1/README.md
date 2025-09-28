# Peluquería de Mascotas - MVP Spring Boot

Este proyecto es un MVP de un sistema de gestión para una peluquería de mascotas construido con Spring Boot 3, Thymeleaf y Bootstrap 5. Incluye autenticación con Spring Security, APIs REST y vistas web responsivas para administrar clientes, mascotas, servicios, citas, pagos, productos y ventas.

## Requisitos previos
- Java 21
- Maven 3.9+
- MySQL 8+ con la base de datos `PeluqueriaMascotas` creada previamente

## Configuración
1. Actualiza las credenciales de la base de datos en [`application.properties`](src/main/resources/application.properties) si es necesario.
2. Ejecuta el proyecto con:
   ```bash
   ./mvnw spring-boot:run
   ```
3. Ingresa a `http://localhost:8080` e inicia sesión con alguno de los usuarios de ejemplo:
   - Administrador: `admin@peluqueria.com` / `admin123`
   - Recepcionista: `recepcion@peluqueria.com` / `recepcion123`

Al iniciar por primera vez se cargan datos de ejemplo a través de `DataInitializer`.

## Módulos principales
- Panel de control con métricas y gráficos.
- CRUD visual con modales y validaciones para usuarios, clientes, mascotas, servicios, citas, pagos, productos y ventas.
- API REST para integraciones externas (`/api/**`).

## Tecnologías
- Spring Boot 3
- Spring Data JPA (Hibernate)
- Spring Security con BCrypt
- Thymeleaf + Bootstrap 5
- Lombok

## Estructura destacada
- Entidades en `com.sise.model` con anotaciones JPA y validaciones.
- Servicios en `com.sise.service` con la lógica de negocio y agregaciones del dashboard.
- Controladores web en `com.sise.web.controller` y APIs REST en `com.sise.web.api`.
- Vistas Thymeleaf en `src/main/resources/templates` con componentes reutilizables y estilo moderno.

## Testing
Para verificar que el proyecto compila (requiere acceso a Maven Central):
```bash
./mvnw -DskipTests compile
```

> Nota: en entornos sin acceso a internet es necesario contar con un mirror local de Maven o ejecutar el comando con una instalación de Maven ya disponible en el sistema.
