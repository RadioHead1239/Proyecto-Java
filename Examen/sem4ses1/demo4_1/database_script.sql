-- Script de Base de Datos MySQL para Sistema de Peluquería de Mascotas
-- Basado en las entidades del proyecto Spring Boot

-- Crear base de datos
CREATE DATABASE IF NOT EXISTS peluqueria_mascotas;
USE peluqueria_mascotas;

-- Tabla usuario
CREATE TABLE usuario (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    rol ENUM('Administrador', 'Recepcionista', 'Peluquero') NOT NULL,
    imagen VARCHAR(255),
    estado BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion DATETIME
);

-- Tabla cliente
CREATE TABLE cliente (
    id_cliente BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    correo VARCHAR(100) UNIQUE,
    direccion VARCHAR(255),
    imagen VARCHAR(255),
    fecha_registro DATETIME
);

-- Tabla mascota
CREATE TABLE mascota (
    id_mascota BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_cliente BIGINT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    especie ENUM('Perro', 'Gato', 'Otro') NOT NULL,
    raza VARCHAR(100),
    edad INT,
    peso DECIMAL(5,2),
    observaciones TEXT,
    imagen VARCHAR(255),
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente) ON DELETE CASCADE
);

-- Tabla servicio
CREATE TABLE servicio (
    id_servicio BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL,
    duracion_minutos INT NOT NULL,
    imagen VARCHAR(255),
    categoria VARCHAR(50),
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

-- Tabla producto
CREATE TABLE producto (
    id_producto BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    stock_minimo INT DEFAULT 0,
    imagen VARCHAR(255),
    categoria VARCHAR(50),
    marca VARCHAR(50),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion DATETIME
);

-- Tabla cita
CREATE TABLE cita (
    id_cita BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_cliente BIGINT NOT NULL,
    id_mascota BIGINT NOT NULL,
    id_servicio BIGINT NOT NULL,
    id_usuario BIGINT NOT NULL,
    fecha_cita DATETIME NOT NULL,
    estado ENUM('Pendiente', 'Confirmada', 'Cancelada', 'Completada') NOT NULL DEFAULT 'Pendiente',
    observaciones TEXT,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente) ON DELETE CASCADE,
    FOREIGN KEY (id_mascota) REFERENCES mascota(id_mascota) ON DELETE CASCADE,
    FOREIGN KEY (id_servicio) REFERENCES servicio(id_servicio) ON DELETE CASCADE,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

-- Tabla pago
CREATE TABLE pago (
    id_pago BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_cita BIGINT NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    fecha_pago DATETIME,
    metodo_pago ENUM('Efectivo', 'Tarjeta', 'Transferencia', 'Yape', 'Plin') NOT NULL,
    estado ENUM('Pendiente', 'Completado', 'Cancelado', 'Reembolsado') NOT NULL DEFAULT 'Pendiente',
    observaciones TEXT,
    FOREIGN KEY (id_cita) REFERENCES cita(id_cita) ON DELETE CASCADE
);

-- Tabla venta
CREATE TABLE venta (
    id_venta BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_cliente BIGINT NOT NULL,
    id_usuario BIGINT NOT NULL,
    id_cita BIGINT NULL,
    fecha DATETIME,
    total DECIMAL(10,2) NOT NULL,
    estado ENUM('Pendiente', 'Completada', 'Cancelada', 'Reembolsada') NOT NULL DEFAULT 'Pendiente',
    observaciones TEXT,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente) ON DELETE CASCADE,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_cita) REFERENCES cita(id_cita) ON DELETE SET NULL
);

-- Tabla detalle_venta
CREATE TABLE detalle_venta (
    id_detalle BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_venta BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_venta) REFERENCES venta(id_venta) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto) ON DELETE CASCADE
);

-- Índices para mejorar el rendimiento
CREATE INDEX idx_usuario_correo ON usuario(correo);
CREATE INDEX idx_cliente_correo ON cliente(correo);
CREATE INDEX idx_mascota_cliente ON mascota(id_cliente);
CREATE INDEX idx_cita_fecha ON cita(fecha_cita);
CREATE INDEX idx_cita_cliente ON cita(id_cliente);
CREATE INDEX idx_cita_estado ON cita(estado);
CREATE INDEX idx_venta_fecha ON venta(fecha);
CREATE INDEX idx_venta_cliente ON venta(id_cliente);
CREATE INDEX idx_producto_activo ON producto(activo);
CREATE INDEX idx_servicio_activo ON servicio(activo);

-- Script de migración para bases de datos existentes
-- Ejecutar solo si la tabla venta ya existe sin la columna id_cita
-- ALTER TABLE venta ADD COLUMN id_cita BIGINT NULL;
-- ALTER TABLE venta ADD FOREIGN KEY (id_cita) REFERENCES cita(id_cita) ON DELETE SET NULL;

-- Datos iniciales (opcional)
-- Usuario administrador por defecto
INSERT INTO usuario (nombre, correo, password, rol, estado, fecha_creacion) 
VALUES ('Administrador', 'admin@peluqueria.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXIHB3Q8Q7N9z5L5Y5Y5Y5Y5Y5Y', 'Administrador', TRUE, NOW());

-- Servicios básicos
INSERT INTO servicio (nombre, descripcion, precio, duracion_minutos, categoria, activo) VALUES
('Baño y Secado', 'Baño completo con champú especializado y secado', 25.00, 60, 'Higiene', TRUE),
('Corte de Pelo', 'Corte de pelo según raza y preferencias del cliente', 35.00, 90, 'Estética', TRUE),
('Corte de Uñas', 'Corte y limado de uñas', 15.00, 30, 'Higiene', TRUE),
('Limpieza de Oídos', 'Limpieza profunda de oídos', 20.00, 30, 'Salud', TRUE),
('Cepillado y Desenredado', 'Cepillado profundo y eliminación de nudos', 18.00, 45, 'Higiene', TRUE);

-- Productos básicos
INSERT INTO producto (nombre, descripcion, precio, stock, stock_minimo, categoria, marca, activo, fecha_creacion) VALUES
('Champú para Perros', 'Champú especializado para perros de todas las razas', 12.50, 50, 10, 'Higiene', 'PetCare', TRUE, NOW()),
('Champú para Gatos', 'Champú suave especializado para gatos', 14.00, 30, 5, 'Higiene', 'CatCare', TRUE, NOW()),
('Cepillo Desenredador', 'Cepillo especializado para eliminar nudos', 18.00, 25, 5, 'Herramientas', 'GroomPro', TRUE, NOW()),
('Cortauñas Profesional', 'Cortauñas profesional para mascotas', 22.00, 15, 3, 'Herramientas', 'PetTools', TRUE, NOW()),
('Toallas Absorbentes', 'Toallas especiales para secado de mascotas', 8.50, 100, 20, 'Accesorios', 'DryPet', TRUE, NOW());
