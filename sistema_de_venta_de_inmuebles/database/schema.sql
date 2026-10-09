CREATE DATABASE IF NOT EXISTS inmobiliaria;
USE inmobiliaria;

CREATE TABLE IF NOT EXISTS inmobiliaria (
    id INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS administrador (
    id_inmobiliaria INT NOT NULL,
    id INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    clave VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_inmobiliaria, id),
    CONSTRAINT fk_administrador_inmobiliaria
        FOREIGN KEY (id_inmobiliaria) REFERENCES inmobiliaria(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS propietario (
    id_inmobiliaria INT NOT NULL,
    id INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    clave VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_inmobiliaria, id),
    CONSTRAINT fk_propietario_inmobiliaria
        FOREIGN KEY (id_inmobiliaria) REFERENCES inmobiliaria(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS cliente (
    id_inmobiliaria INT NOT NULL,
    id INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    PRIMARY KEY (id_inmobiliaria, id),
    CONSTRAINT fk_cliente_inmobiliaria
        FOREIGN KEY (id_inmobiliaria) REFERENCES inmobiliaria(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS inmueble (
    id_inmobiliaria INT NOT NULL,
    id INT NOT NULL,
    ubicacion VARCHAR(150) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    precio DECIMAL(15, 2) NOT NULL,
    tipo_contrato VARCHAR(20) NOT NULL,
    tipo_inmueble VARCHAR(30) NOT NULL,
    esta_disponible BOOLEAN NOT NULL,
    PRIMARY KEY (id_inmobiliaria, id),
    CONSTRAINT fk_inmueble_inmobiliaria
        FOREIGN KEY (id_inmobiliaria) REFERENCES inmobiliaria(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS propietario_inmueble (
    id_inmobiliaria INT NOT NULL,
    id_propietario INT NOT NULL,
    id_inmueble INT NOT NULL,
    PRIMARY KEY (id_inmobiliaria, id_propietario, id_inmueble),
    CONSTRAINT fk_propietario_inmueble_propietario
        FOREIGN KEY (id_inmobiliaria, id_propietario)
        REFERENCES propietario(id_inmobiliaria, id) ON DELETE CASCADE,
    CONSTRAINT fk_propietario_inmueble_inmueble
        FOREIGN KEY (id_inmobiliaria, id_inmueble)
        REFERENCES inmueble(id_inmobiliaria, id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS administrador_inmueble (
    id_inmobiliaria INT NOT NULL,
    id_administrador INT NOT NULL,
    id_inmueble INT NOT NULL,
    PRIMARY KEY (id_inmobiliaria, id_administrador, id_inmueble),
    CONSTRAINT fk_administrador_inmueble_administrador
        FOREIGN KEY (id_inmobiliaria, id_administrador)
        REFERENCES administrador(id_inmobiliaria, id) ON DELETE CASCADE,
    CONSTRAINT fk_administrador_inmueble_inmueble
        FOREIGN KEY (id_inmobiliaria, id_inmueble)
        REFERENCES inmueble(id_inmobiliaria, id) ON DELETE CASCADE
);
