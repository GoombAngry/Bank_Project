-- Eliminar tablas si existen
DROP TABLE IF EXISTS  bank.movimiento_tarjeta_bancaria;
DROP TABLE IF EXISTS  bank.movimiento_cuenta_bancaria;
DROP TABLE IF EXISTS bank.token;
DROP TABLE IF EXISTS bank.tarjeta_bancaria;
DROP TABLE IF EXISTS bank.cuenta_bancaria;
DROP TABLE IF EXISTS bank.tipo_tarjeta;
DROP TABLE IF EXISTS bank.tipo_cuenta;
DROP TABLE IF EXISTS bank.cliente;

-- Eliminar la base de datos si existe
DROP SCHEMA IF EXISTS bank;
-- Crear Base de datos
CREATE SCHEMA bank;
-- Usar Base de datos
USE bank;
CREATE TABLE cliente (
    id_cliente INT PRIMARY KEY AUTO_INCREMENT,  -- Identificador único y autoincremental
    dni_cliente VARCHAR(9) NOT NULL UNIQUE,  -- DNI con una longitud estándar de 9 caracteres
    nombre VARCHAR(100) NOT NULL,  -- Limitar el nombre a 100 caracteres por seguridad
    apellidos VARCHAR(200) NOT NULL,  -- Los apellidos pueden ser más largos que el nombre
    correo_electronico VARCHAR(255) NOT NULL UNIQUE,  -- Para almacenar emails correctamente
    password VARCHAR(255) NOT NULL,  -- Almacenar contraseñas de manera segura (máxima longitud)
    fecha_alta DATETIME NOT NULL,  -- Fecha de alta del cliente
    is_baja BOOLEAN DEFAULT FALSE, -- Indica si el cliente esta dado de bajo en nuestro banco
    telefono VARCHAR(15) NOT NULL,  -- Teléfono para contacto 
    direccion TEXT  NOT NULL, -- Direccion de contacto
    ciudad TEXT NOT NULL -- Ciudad del contacto
);

CREATE TABLE tipo_token (
	id_tipo_token INT PRIMARY KEY AUTO_INCREMENT,
	tipo_nombre VARCHAR(255) UNIQUE NOT NULL
);
INSERT INTO tipo_token (tipo_nombre) VALUES ('APP');
INSERT INTO tipo_token (tipo_nombre) VALUES ('ATM_SYSTEM');

CREATE TABLE token (
    id_token INT PRIMARY KEY AUTO_INCREMENT,      -- Clave primaria con auto-incremento
    id_cliente INT,                               -- Clave foránea hacia la tabla cliente
    id_tipo_token INT,			          -- Clave foranea para indicar a que sistema esta asociado el token (app o cajero)
    token VARCHAR(255) UNIQUE,                    -- Columna para almacenar el token (cadena única)
    token_type VARCHAR(255) NOT NULL,              -- Columna para almacenar el valor del enum (en formato cadena)
    revoked BOOLEAN,                              -- Columna para indicar si el token está revocado
    expired BOOLEAN,                              -- Columna para indicar si el token ha expirado
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),  -- Clave foránea hacia la tabla cliente
    FOREIGN KEY (id_tipo_token) REFERENCES tipo_token(id_tipo_token) -- Clave foránea hacia la tabla tipo_token
);

CREATE TABLE tipo_cuenta (
    id_tipo_cuenta INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL UNIQUE,  -- Nombre del tipo de cuenta (ej. "AHORRO", "CORRIENTE")
    descripcion TEXT  -- Descripción opcional del tipo de cuenta
);
INSERT INTO tipo_cuenta (nombre, descripcion) VALUES('AHORRO','Cuenta de Ahorro');
INSERT INTO tipo_cuenta (nombre, descripcion) VALUES('CORRIENTE','Cuenta Corriente');
CREATE TABLE cuenta_bancaria (
    id_cuenta_bancaria INT PRIMARY KEY AUTO_INCREMENT,
    id_cliente INT NOT NULL,  -- Relación con Clientes (Tabla)
    id_tipo_cuenta INT NOT NULL,  -- Relacion con Tipo_Cuentas (Tabla)
    numero_cuenta VARCHAR(20) NOT NULL UNIQUE,  -- Número de cuenta BANK-XXXXXXXXXXXXXX
    saldo DECIMAL(15, 2) NOT NULL DEFAULT 0,  -- Saldo de la cuenta 
    fecha_creacion DATETIME NOT NULL,  -- Fecha de creación de la cuenta
    fecha_cierre DATETIME NULL,  -- Fecha de cierre de la cuenta
    is_activa BOOLEAN DEFAULT TRUE,  -- Marca si la cuenta está activa (TRUE) o cerrada (FALSE)
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),  -- Relación con la tabla Clientes
    FOREIGN KEY (id_tipo_cuenta) REFERENCES tipo_cuenta(id_tipo_cuenta)  -- Relación con la tabla Tipos_Cuentas
);
-- Regla para que mientras que este activa la cuenta el valor de fecha_cierre sea null
ALTER TABLE cuenta_bancaria
ADD CONSTRAINT check_fecha_cierre CHECK (
    (is_activa = TRUE AND fecha_cierre IS NULL) OR 
    (is_activa = FALSE AND fecha_cierre IS NOT NULL)
);

CREATE TABLE movimiento_cuenta_bancaria (
	id_movimiento_cuenta_bancaria INT PRIMARY KEY AUTO_INCREMENT,
	id_cuenta_bancaria INT NOT NULL, -- Representa el id de la cuenta bancaria que hace el movimiento
	tipo_movimiento VARCHAR(50), --  Guardara el tipo de movimiento {"INGRESO", "RETIRO", "TRANSFERENCIA"}
	cantidad DECIMAL(15,2), -- Indica la cantida implicada en el movimiento
	fecha_movimiento DATETIME NOT NULL, -- Fecha del dia exacto que se hizo este movimiento con la cuenta bancaria
	descripcion TEXT, -- Una breve descripcion del movimiento realizado
	remitente_identificador VARCHAR(30), -- Se guardara el identificador de la cuenta bancaria del remitente - Número de tarjeta XXXXXXXXXXXX
	destinatario_identificador VARCHAR(30), -- Se guardara el identificador de la tarjeta (Número de tarjeta XXXXXXXXXXXX) o cuenta bancaria (Número de cuenta BANK-XXXXXXXXXXXXXX) destinatario
	FOREIGN KEY (id_cuenta_bancaria) REFERENCES cuenta_bancaria(id_cuenta_bancaria)
);

CREATE TABLE tipo_tarjeta (
	id_tipo_tarjeta INT PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(50) NOT NULL UNIQUE, -- (Credito o Debito)
	descripcion TEXT
);
INSERT INTO tipo_tarjeta (nombre,descripcion) VALUES ('DEBITO','EL saldo se carga a la cuenta titular(siempre y cuando disponga del salgo) de dicha tarjeta');
INSERT INTO tipo_tarjeta (nombre,descripcion) VALUES ('CREDITO','El saldo se carga a la tarjeta y posteriormente se pasa a la cuenta(independientemente de si dispone de saldo o no)');
CREATE TABLE tarjeta_bancaria (
    id_tarjeta_bancaria INT PRIMARY KEY AUTO_INCREMENT,
    id_cliente INT NOT NULL,  -- Relación con Clientes
    id_cuenta_bancaria INT NOT NULL,   -- Una tarjeta esta vinculada si o si a una cuenta
    id_tipo_tarjeta INT NOT NULL,  -- Indica el tipo de tarjeta bancaria (DEBITO o CREDITO)
    numero_tarjeta VARCHAR(19) NOT NULL UNIQUE,  -- Número de tarjeta XXXXXXXXXXXX
    cv_tarjeta VARCHAR(255) NOT NULL,  -- Almacenar contraseñas de manera segura (máxima longitud)
    limite_tarjeta DECIMAL(15, 2) NOT NULL,  -- Maximo que puede gastar de golpe la tarjeta
    saldo DECIMAL(15, 2) DEFAULT 0,  -- Saldo pendiente de pago
    fecha_emision DATETIME NOT NULL,  -- Fecha en la que se emitió la tarjeta
    fecha_vencimiento DATETIME NOT NULL,  -- Fecha de vencimiento
    bloqueada BOOLEAN DEFAULT FALSE,  -- Indicador de bloqueo de la tarjeta
    is_activa BOOLEAN DEFAULT TRUE,  -- Marca si la tarjeta está activa (TRUE) o cerrada (FALSE)
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),  -- Relación con la tabla Clientes
    FOREIGN KEY (id_cuenta_bancaria) REFERENCES cuenta_bancaria(id_cuenta_bancaria),
    FOREIGN KEY (id_tipo_tarjeta) REFERENCES tipo_tarjeta(id_tipo_tarjeta)
);
ALTER TABLE tarjeta_bancaria
ADD CONSTRAINT check_fecha_vencimiento CHECK (fecha_vencimiento > fecha_emision);

CREATE TABLE movimiento_tarjeta_bancaria (
	id_movimiento_tarjeta_bancaria INT PRIMARY KEY AUTO_INCREMENT,
	id_tarjeta_bancaria INT NOT NULL, -- Representa el id de la tarjeta bancaria que hace el movimiento
	tipo_movimiento VARCHAR(50), --  Guardara el tipo de movimiento {"CARGO", "REMBOLSO", "RETIRO","INGRESO"}
	cantidad DECIMAL(15,2), -- Indica la cantida implicada en el movimiento
	fecha_movimiento DATETIME NOT NULL, -- Fecha del dia exacto que se hizo este movimiento con la tarjeta
	descripcion TEXT, -- Una breve descripcion del movimiento realizado
	remitente_identificador VARCHAR(30), -- Se guardara el identificador de la tarjeta bancaria del remitente - Número de tarjeta XXXXXXXXXXXX
	destinatario_identificador VARCHAR(30), -- Se guardara el identificador de la tarjeta (Número de tarjeta XXXXXXXXXXXX) o cuenta bancaria (Número de cuenta BANK-XXXXXXXXXXXXXX) destinatario
	FOREIGN KEY (id_tarjeta_bancaria) REFERENCES tarjeta_bancaria(id_tarjeta_bancaria)
);

DELIMITER $$

-- Trigger para controlar el tipo de tarjeta bancaria cuando se crea
CREATE TRIGGER antes_insertar_tarjeta_bancaria
BEFORE INSERT ON tarjeta_bancaria
FOR EACH ROW
BEGIN
    -- Verifica si el tipo de tarjeta es de DEBITO (id_tipo_tarjeta = 1)
    IF NEW.id_tipo_tarjeta = 1 THEN
        -- Asigna saldo y limite_tarjeta como NULL 
        SET NEW.saldo = NULL;
    END IF;
END$$

DELIMITER ;

