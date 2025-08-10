-- codigo para que tome los caracteres especiales
ALTER DATABASE petsnagoa
CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

-- cositas para eliminar algo por siacaso
-- DROP TABLE IF EXISTS factura;

-- creamos la base de datos
create database petsnagoa;

-- que utilice la base de datos 
use petsnagoa;

-- creamos las tablas con su respectiva relacion

-- tabla cliente
CREATE TABLE cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    cod_cliente varchar(15) unique not null,
    nombre_cliente VARCHAR(50) NOT NULL,
    apellido_cliente VARCHAR(50) NOT NULL,
    correo_cliente VARCHAR(50) NOT NULL,
    telefono_cliente VARCHAR(50) NOT NULL,
    direccion_cliente VARCHAR(50) NOT NULL
);

-- selecionar la tabla cliente
select * from cliente;

-- Tabla del proveedor
CREATE TABLE proveedor (
    id_proveedor INT AUTO_INCREMENT PRIMARY KEY,
    nif VARCHAR(20) UNIQUE NOT NULL,
    nombre_proveedor VARCHAR(50) NOT NULL,
    correo_proveedor VARCHAR(50) NOT NULL,
    telefono_proveedor VARCHAR(50) NOT NULL,
    direccion_proveedor VARCHAR(50) NOT NULL
);

-- agregamos proveedores
insert into proveedor(nif,nombre_proveedor,correo_proveedor,telefono_proveedor,direccion_proveedor) values ('781346','Paola','paola@gmail.com','041983','Av.universidad');
insert into proveedor(nif,nombre_proveedor,correo_proveedor,telefono_proveedor,direccion_proveedor) values ('71902','Pablo','pablovillegas@gmail.com','04588779','Av.penme');
insert into proveedor(nif,nombre_proveedor,correo_proveedor,telefono_proveedor,direccion_proveedor) values ('1812','Carla','carla15@gmail.com','0415643','Av.universidad');
insert into proveedor(nif,nombre_proveedor,correo_proveedor,telefono_proveedor,direccion_proveedor) values ('617','ComesticosP','tupuntocosmetics@gmail.com','0425478','Calle.Guajira');
insert into proveedor(nif,nombre_proveedor,correo_proveedor,telefono_proveedor,direccion_proveedor) values ('106159','Mariospets','mascotatuvida@gmail.com','0412983','Mañongo');

-- selecionamos la tabla para ver si se agrego con exito
select * from proveedor;

-- Tabla para el cajero
CREATE TABLE cajero (
	id_cajero INT AUTO_INCREMENT PRIMARY KEY,
    nombre_cajero VARCHAR(30) NOT NULL,
    apellido_cajero VARCHAR(30) NOT NULL,
    usuario_cajero VARCHAR(30)  unique NOT NULL,
    contraseña_cajero VARCHAR(50) NOT NULL
);

-- Seleciona para ver si existe en la base de datos
select * from cajero;

CREATE TABLE producto (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    cod_producto VARCHAR(30) UNIQUE NOT NULL,
    nombre_producto VARCHAR(50) NOT NULL,
    precio_producto DECIMAL(10,2) NOT NULL,
    cantidad_producto INT NOT NULL,
    id_proveedor INT NOT NULL,
    FOREIGN KEY (id_proveedor) REFERENCES proveedor(id_proveedor)
);

-- Seleccion para ver si agrego correctamente los productos
select * from producto;
-- Insertamos los productos en la base de datos
INSERT INTO producto (cod_producto, nombre_producto, precio_producto, cantidad_producto, id_proveedor)
VALUES 
('P001', 'Collar para perro', 12.50, 100, 1),
('P002', 'Comida para gato 1kg', 8.75, 50, 1),
('P003', 'Juguete para hámster', 5.20, 30, 5),
('P004', 'Arena para gato 5kg', 15.00, 40, 5),('P005', 'Arena para gato 10kg', 35.00, 80, 5),('P006', 'Camisa de rayas gato', 5.30, 40, 4),('P008', 'Perrarina PetChao 10 kg', 50.80, 100, 4),('P009', 'Peine para perros', 3.50, 15, 4),('P010', 'Zapatitos para perro', 12.30, 16, 2),('P011', 'Arena para gato 5kg', 15.00, 40, 3);

-- tabla factura
CREATE TABLE factura (
    id_factura INT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT NOT NULL,
    id_cliente INT NOT NULL,
    id_cajero INT NOT NULL,
    forma_pago VARCHAR(30) NOT NULL,  -- Ej: 'Efectivo', 'Tarjeta', 'Transferencia'
    fechayhora_factura TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto),
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
    FOREIGN KEY (id_cajero) REFERENCES cajero(id_cajero)
);

-- Para selecionar que exista la factura
select * from factura;
