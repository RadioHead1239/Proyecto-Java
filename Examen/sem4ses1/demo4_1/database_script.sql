CREATE DATABASE IF NOT EXISTS peluqueria_mascotas;
USE peluqueria_mascotas;

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

