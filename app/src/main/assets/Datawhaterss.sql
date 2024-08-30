<?xml version="1.0" encoding="UTF-8"?><sqlb_project><db path="C:/Users/Usuario/Desktop/Datawhater.sqlite" readonly="0" foreign_keys="1" case_sensitive_like="0" temp_store="0" wal_autocheckpoint="1000" synchronous="2"/><attached/><window><main_tabs open="structure browser pragmas query" current="3"/></window><tab_structure><column_width id="0" width="300"/><column_width id="1" width="0"/><column_width id="2" width="100"/><column_width id="3" width="3465"/><column_width id="4" width="0"/><expanded_item id="0" parent="1"/><expanded_item id="0" parent="0"/><expanded_item id="1" parent="0"/><expanded_item id="2" parent="0"/><expanded_item id="3" parent="0"/><expanded_item id="4" parent="0"/><expanded_item id="1" parent="1"/><expanded_item id="2" parent="1"/><expanded_item id="3" parent="1"/></tab_structure><tab_browse><current_table name="4,9:mainProductos"/><default_encoding codec=""/><browse_table_settings><table schema="main" name="Almacenes" show_row_id="0" encoding="" plot_x_axis="" unlock_view_pk="_rowid_"><sort><column index="3" mode="0"/></sort><column_widths><column index="1" value="77"/><column index="2" value="108"/><column index="3" value="113"/></column_widths><filter_values/><conditional_formats/><row_id_formats/><display_formats/><hidden_columns/><plot_y_axes/><global_filter/></table><table schema="main" name="Categorias" show_row_id="0" encoding="" plot_x_axis="" unlock_view_pk="_rowid_"><sort/><column_widths><column index="1" value="85"/><column index="2" value="69"/><column index="3" value="204"/></column_widths><filter_values/><conditional_formats/><row_id_formats/><display_formats/><hidden_columns/><plot_y_axes/><global_filter/></table><table schema="main" name="MovimientosInventario" show_row_id="0" encoding="" plot_x_axis="" unlock_view_pk="_rowid_"><sort/><column_widths><column index="1" value="96"/><column index="2" value="81"/><column index="3" value="129"/><column index="4" value="108"/><column index="5" value="65"/><column index="6" value="77"/><column index="7" value="277"/></column_widths><filter_values/><conditional_formats/><row_id_formats/><display_formats/><hidden_columns/><plot_y_axes/><global_filter/></table><table schema="main" name="Productos" show_row_id="0" encoding="" plot_x_axis="" unlock_view_pk="_rowid_"><sort/><column_widths><column index="1" value="81"/><column index="2" value="84"/><column index="3" value="160"/><column index="4" value="48"/><column index="5" value="44"/><column index="6" value="85"/><column index="7" value="90"/></column_widths><filter_values/><conditional_formats/><row_id_formats/><display_formats/><hidden_columns/><plot_y_axes/><global_filter/></table></browse_table_settings></tab_browse><tab_sql><sql name="DATAwather.sql">-- Eliminar tablas si ya existen (esto eliminará los datos existentes)

DROP TABLE IF EXISTS MovimientosInventario;

DROP TABLE IF EXISTS Productos;

DROP TABLE IF EXISTS Proveedores;

DROP TABLE IF EXISTS Categorias;

DROP TABLE IF EXISTS Almacenes;



-- Crear tabla de Almacenes

CREATE TABLE IF NOT EXISTS Almacenes (

    AlmacenID INTEGER PRIMARY KEY AUTOINCREMENT,

    Nombre VARCHAR(100) NOT NULL,

    Ubicacion TEXT

);



-- Crear tabla de Categorías

CREATE TABLE IF NOT EXISTS Categorias (

    CategoriaID INTEGER PRIMARY KEY AUTOINCREMENT,

    Nombre VARCHAR(100) NOT NULL,

    Descripcion TEXT

);



-- Crear tabla de Proveedores

CREATE TABLE IF NOT EXISTS Proveedores (

    ProveedorID INTEGER PRIMARY KEY AUTOINCREMENT,

    Nombre VARCHAR(100) NOT NULL,

    Contacto VARCHAR(100),

    Telefono VARCHAR(20),

    Email VARCHAR(100)

);



-- Crear tabla de Productos

CREATE TABLE IF NOT EXISTS Productos (

    ProductoID INTEGER PRIMARY KEY AUTOINCREMENT,

    Nombre VARCHAR(100) NOT NULL,

    Descripcion TEXT,

    Precio DECIMAL(10, 2) NOT NULL,

    Stock INTEGER NOT NULL,

    CategoriaID INTEGER,

    ProveedorID INTEGER,

    FOREIGN KEY (CategoriaID) REFERENCES Categorias(CategoriaID),

    FOREIGN KEY (ProveedorID) REFERENCES Proveedores(ProveedorID)

);



-- Crear tabla de Movimientos de Inventario

CREATE TABLE IF NOT EXISTS MovimientosInventario (

    MovimientoID INTEGER PRIMARY KEY AUTOINCREMENT,

    ProductoID INTEGER,

    Fecha DATETIME NOT NULL,

    TipoMovimiento VARCHAR(10) NOT NULL CHECK (TipoMovimiento IN ('Entrada', 'Salida')),

    Cantidad INTEGER NOT NULL,

    AlmacenID INTEGER,

    Comentarios TEXT,

    FOREIGN KEY (ProductoID) REFERENCES Productos(ProductoID),

    FOREIGN KEY (AlmacenID) REFERENCES Almacenes(AlmacenID)

);



-- Insertar algunos almacenes de ejemplo

INSERT INTO Almacenes (Nombre, Ubicacion) VALUES

('Almacén Principal', 'Ubicación Principal'),

('Bodega', 'Ubicación Bodega');



-- Insertar algunas categorías de ejemplo

INSERT INTO Categorias (Nombre, Descripcion) VALUES

('Electrónica', 'Dispositivos electrónicos y gadgets'),

('Ropa', 'Prendas de vestir y accesorios'),

('Alimentos', 'Productos alimenticios y bebidas');



-- Insertar algunos proveedores de ejemplo

INSERT INTO Proveedores (Nombre, Contacto, Telefono, Email) VALUES

('com.example.stockingsql.com.example.stockingsql.com.example.stockingsql.com.example.stockingsql.Proveedor A', 'Juan Pérez', '555-1234', 'juan@proveedora.com'),

('com.example.stockingsql.com.example.stockingsql.com.example.stockingsql.com.example.stockingsql.Proveedor B', 'María García', '555-5678', 'maria@proveedorb.com');



-- Insertar algunos productos de ejemplo

INSERT INTO Productos (Nombre, Descripcion, Precio, Stock, CategoriaID, ProveedorID) VALUES

('Laptop', 'Laptop de 15 pulgadas', 1200.00, 10, 1, 1),

('Camiseta', 'Camiseta de algodón', 20.00, 50, 2, 2),

('Galletas', 'Galletas de chocolate', 3.00, 100, 3, 1);



-- Insertar algunos movimientos de inventario de ejemplo

INSERT INTO MovimientosInventario (ProductoID, Fecha, TipoMovimiento, Cantidad, AlmacenID, Comentarios) VALUES

(1, '2024-06-01 10:00:00', 'Entrada', 5, 1, 'Recepción de nuevo stock en Almacén Principal'),

(2, '2024-06-02 14:30:00', 'Salida', 2, 1, 'Venta de producto desde Almacén Principal'),

(3, '2024-06-03 09:15:00', 'Entrada', 50, 2, 'Recepción de nuevo stock en Bodega');



-- Consultar almacenes

SELECT * FROM Almacenes;



-- Consultar categorías

SELECT * FROM Categorias;



-- Consultar proveedores

SELECT * FROM Proveedores;



-- Consultar productos con categoría y proveedor

SELECT

    p.ProductoID,

    p.Nombre AS NombreProducto,

    p.Descripcion,

    p.Precio,

    p.Stock,

    c.Nombre AS NombreCategoria,

    pr.Nombre AS NombreProveedor

FROM

    Productos p

JOIN

    Categorias c ON p.CategoriaID = c.CategoriaID

JOIN

    Proveedores pr ON p.ProveedorID = pr.ProveedorID;



-- Consultar movimientos de inventario por almacén

SELECT

    m.MovimientoID,

    p.Nombre AS NombreProducto,

    m.Fecha,

    m.TipoMovimiento,

    m.Cantidad,

    a.Nombre AS NombreAlmacen,

    m.Comentarios

FROM

    MovimientosInventario m

JOIN

    Productos p ON m.ProductoID = p.ProductoID

JOIN

    Almacenes a ON m.AlmacenID = a.AlmacenID

ORDER BY

    m.Fecha DESC;



-- Ejemplos de operaciones CRUD adicionales



-- Crear una nueva categoría

INSERT INTO Categorias (Nombre, Descripcion) VALUES ('Hogar', 'Productos para el hogar');



-- Crear un nuevo proveedor

INSERT INTO Proveedores (Nombre, Contacto, Telefono, Email) VALUES ('com.example.stockingsql.com.example.stockingsql.com.example.stockingsql.com.example.stockingsql.Proveedor C', 'Carlos López', '555-6789', 'carlos@proveedorc.com');



-- Crear un nuevo producto

INSERT INTO Productos (Nombre, Descripcion, Precio, Stock, CategoriaID, ProveedorID) VALUES ('Televisor LED', 'Televisor de alta definición', 799.99, 20, 1, 1);



-- Registrar un nuevo movimiento de inventario

INSERT INTO MovimientosInventario (ProductoID, Fecha, TipoMovimiento, Cantidad, AlmacenID, Comentarios) VALUES (2, '2024-06-05 09:00:00', 'Entrada', 10, 2, 'Recepción de nuevo stock en Bodega');



-- Actualizar stock de un producto

UPDATE Productos SET Stock = Stock + 5 WHERE ProductoID = 2;



-- Eliminar movimientos de inventario asociados al producto antes de eliminar el producto

DELETE FROM MovimientosInventario WHERE ProductoID = 3;



-- Eliminar un producto

DELETE FROM Productos WHERE ProductoID = 3;



-- Consultar productos actualizados con categoría y proveedor

SELECT

    p.ProductoID,

    p.Nombre AS NombreProducto,

    p.Descripcion,

    p.Precio,

    p.Stock,

    c.Nombre AS NombreCategoria,

    pr.Nombre AS NombreProveedor

FROM

    Productos p

JOIN

    Categorias c ON p.CategoriaID = c.CategoriaID

JOIN

    Proveedores pr ON p.ProveedorID = pr.ProveedorID;



-- Consultar movimientos de inventario actualizados por almacén

SELECT

    m.MovimientoID,

    p.Nombre AS NombreProducto,

    m.Fecha,

    m.TipoMovimiento,

    m.Cantidad,

    a.Nombre AS NombreAlmacen,

    m.Comentarios

FROM

    MovimientosInventario m

JOIN

    Productos p ON m.ProductoID = p.ProductoID

JOIN

    Almacenes a ON m.AlmacenID = a.AlmacenID

ORDER BY

    m.Fecha DESC;

</sql><sql name="Datawhater2.sql">-- Desactivar temporalmente las restricciones de clave externa

PRAGMA foreign_keys = OFF;



-- Eliminar tablas si ya existen (esto eliminará los datos existentes)

DROP TABLE IF EXISTS MovimientosInventario;

DROP TABLE IF EXISTS Productos;

DROP TABLE IF EXISTS Proveedores;

DROP TABLE IF EXISTS Categorias;

DROP TABLE IF EXISTS Almacenes;

DROP TABLE IF EXISTS UnidadesMedida;

DROP TABLE IF EXISTS EstadosProductos;



-- Reactivar las restricciones de clave externa

PRAGMA foreign_keys = ON;



-- Crear tabla de Unidades de Medida

CREATE TABLE IF NOT EXISTS UnidadesMedida (

    UnidadMedidaID INTEGER PRIMARY KEY AUTOINCREMENT,

    Nombre VARCHAR(50) NOT NULL

);



-- Crear tabla de Estados de Productos

CREATE TABLE IF NOT EXISTS EstadosProductos (

    EstadoProductoID INTEGER PRIMARY KEY AUTOINCREMENT,

    Nombre VARCHAR(50) NOT NULL

);



-- Crear tabla de Almacenes

CREATE TABLE IF NOT EXISTS Almacenes (

    AlmacenID INTEGER PRIMARY KEY AUTOINCREMENT,

    Nombre VARCHAR(100) NOT NULL,

    Ubicacion TEXT

);



-- Crear tabla de Categorías

CREATE TABLE IF NOT EXISTS Categorias (

    CategoriaID INTEGER PRIMARY KEY AUTOINCREMENT,

    Nombre VARCHAR(100) NOT NULL,

    Descripcion TEXT

);



-- Crear tabla de Proveedores

CREATE TABLE IF NOT EXISTS Proveedores (

    ProveedorID INTEGER PRIMARY KEY AUTOINCREMENT,

    Nombre VARCHAR(100) NOT NULL,

    Contacto VARCHAR(100),

    Telefono VARCHAR(20),

    Email VARCHAR(100)

);



-- Crear tabla de Productos

CREATE TABLE IF NOT EXISTS Productos (

    ProductoID INTEGER PRIMARY KEY AUTOINCREMENT,

    Nombre VARCHAR(100) NOT NULL,

    Descripcion TEXT,

    Precio DECIMAL(10, 2) NOT NULL,

    Stock INTEGER NOT NULL,

    CategoriaID INTEGER,

    ProveedorID INTEGER,

    UnidadMedidaID INTEGER,

    EstadoProductoID INTEGER,

    FOREIGN KEY (CategoriaID) REFERENCES Categorias(CategoriaID),

    FOREIGN KEY (ProveedorID) REFERENCES Proveedores(ProveedorID),

    FOREIGN KEY (UnidadMedidaID) REFERENCES UnidadesMedida(UnidadMedidaID),

    FOREIGN KEY (EstadoProductoID) REFERENCES EstadosProductos(EstadoProductoID)

);



-- Crear tabla de Movimientos de Inventario

CREATE TABLE IF NOT EXISTS MovimientosInventario (

    MovimientoID INTEGER PRIMARY KEY AUTOINCREMENT,

    ProductoID INTEGER,

    Fecha DATETIME NOT NULL,

    TipoMovimiento VARCHAR(10) NOT NULL CHECK (TipoMovimiento IN ('Entrada', 'Salida', 'Ajuste', 'Transferencia')),

    Cantidad INTEGER NOT NULL,

    AlmacenID INTEGER,

    Comentarios TEXT,

    FOREIGN KEY (ProductoID) REFERENCES Productos(ProductoID),

    FOREIGN KEY (AlmacenID) REFERENCES Almacenes(AlmacenID)

);



-- Insertar algunas unidades de medida de ejemplo

INSERT INTO UnidadesMedida (Nombre) VALUES

('Unidad'),

('Kilogramo'),

('Litro'),

('Caja');



-- Insertar algunos estados de productos de ejemplo

INSERT INTO EstadosProductos (Nombre) VALUES

('Disponible'),

('Reservado'),

('En tránsito'),

('Dañado');



-- Insertar algunos almacenes de ejemplo

INSERT INTO Almacenes (Nombre, Ubicacion) VALUES

('Almacén Principal', 'Ubicación Principal'),

('Bodega', 'Ubicación Bodega');



-- Insertar algunas categorías de ejemplo

INSERT INTO Categorias (Nombre, Descripcion) VALUES

('Electrónica', 'Dispositivos electrónicos y gadgets'),

('Ropa', 'Prendas de vestir y accesorios'),

('Alimentos', 'Productos alimenticios y bebidas');



-- Insertar algunos proveedores de ejemplo

INSERT INTO Proveedores (Nombre, Contacto, Telefono, Email) VALUES

('com.example.stockingsql.com.example.stockingsql.com.example.stockingsql.com.example.stockingsql.Proveedor A', 'Juan Pérez', '555-1234', 'juan@proveedora.com'),

('com.example.stockingsql.com.example.stockingsql.com.example.stockingsql.com.example.stockingsql.Proveedor B', 'María García', '555-5678', 'maria@proveedorb.com');



-- Insertar algunos productos de ejemplo

INSERT INTO Productos (Nombre, Descripcion, Precio, Stock, CategoriaID, ProveedorID, UnidadMedidaID, EstadoProductoID) VALUES

('Laptop', 'Laptop de 15 pulgadas', 1200.00, 10, 1, 1, 1, 1),

('Camiseta', 'Camiseta de algodón', 20.00, 50, 2, 2, 1, 1),

('Galletas', 'Galletas de chocolate', 3.00, 100, 3, 1, 1, 1);



-- Insertar algunos movimientos de inventario de ejemplo

INSERT INTO MovimientosInventario (ProductoID, Fecha, TipoMovimiento, Cantidad, AlmacenID, Comentarios) VALUES

(1, '2024-06-01 10:00:00', 'Entrada', 5, 1, 'Recepción de nuevo stock en Almacén Principal'),

(2, '2024-06-02 14:30:00', 'Salida', 2, 1, 'Venta de producto desde Almacén Principal'),

(3, '2024-06-03 09:15:00', 'Entrada', 50, 2, 'Recepción de nuevo stock en Bodega');



-- Consultar almacenes

SELECT * FROM Almacenes;



-- Consultar categorías

SELECT * FROM Categorias;



-- Consultar proveedores

SELECT * FROM Proveedores;



-- Consultar unidades de medida

SELECT * FROM UnidadesMedida;



-- Consultar estados de productos

SELECT * FROM EstadosProductos;



-- Consultar productos con categoría y proveedor

SELECT

    p.ProductoID,

    p.Nombre AS NombreProducto,

    p.Descripcion,

    p.Precio,

    p.Stock,

    c.Nombre AS NombreCategoria,

    pr.Nombre AS NombreProveedor,

    u.Nombre AS UnidadMedida,

    e.Nombre AS EstadoProducto

FROM

    Productos p

JOIN

    Categorias c ON p.CategoriaID = c.CategoriaID

JOIN

    Proveedores pr ON p.ProveedorID = pr.ProveedorID

JOIN

    UnidadesMedida u ON p.UnidadMedidaID = u.UnidadMedidaID

JOIN

    EstadosProductos e ON p.EstadoProductoID = e.EstadoProductoID;



-- Consultar movimientos de inventario por almacén

SELECT

    m.MovimientoID,

    p.Nombre AS NombreProducto,

    m.Fecha,

    m.TipoMovimiento,

    m.Cantidad,

    a.Nombre AS NombreAlmacen,

    m.Comentarios

FROM

    MovimientosInventario m

JOIN

    Productos p ON m.ProductoID = p.ProductoID

JOIN

    Almacenes a ON m.AlmacenID = a.AlmacenID

ORDER BY

    m.Fecha DESC;



-- Ejemplos de operaciones CRUD adicionales



-- Crear una nueva categoría

INSERT INTO Categorias (Nombre, Descripcion) VALUES ('Hogar', 'Productos para el hogar');



-- Crear un nuevo proveedor

INSERT INTO Proveedores (Nombre, Contacto, Telefono, Email) VALUES ('com.example.stockingsql.com.example.stockingsql.com.example.stockingsql.com.example.stockingsql.Proveedor C', 'Carlos López', '555-6789', 'carlos@proveedorc.com');



-- Crear un nuevo producto

INSERT INTO Productos (Nombre, Descripcion, Precio, Stock, CategoriaID, ProveedorID, UnidadMedidaID, EstadoProductoID) VALUES ('Televisor LED', 'Televisor de alta definición', 799.99, 20, 1, 1, 1, 1);



-- Registrar un nuevo movimiento de inventario

INSERT INTO MovimientosInventario (ProductoID, Fecha, TipoMovimiento, Cantidad, AlmacenID, Comentarios) VALUES (2, '2024-06-05 09:00:00', 'Entrada', 10, 2, 'Recepción de nuevo stock en Bodega');



-- Actualizar stock de un producto

UPDATE Productos SET Stock = Stock + 5 WHERE ProductoID = 2;



-- Eliminar movimientos de inventario asociados al producto antes de eliminar el producto

DELETE FROM MovimientosInventario WHERE ProductoID = 3;



-- Eliminar un producto

DELETE FROM Productos WHERE ProductoID = 3;



-- Consultar productos actualizados con categoría y proveedor

SELECT

    p.ProductoID,

    p.Nombre AS NombreProducto,

    p.Descripcion,

    p.Precio,

    p.Stock,

    c.Nombre AS NombreCategoria,

    pr.Nombre AS NombreProveedor,

    u.Nombre AS UnidadMedida,

    e.Nombre AS EstadoProducto

FROM

    Productos p

JOIN

    Categorias c ON p.CategoriaID = c.CategoriaID

JOIN

    Proveedores pr ON p.ProveedorID = pr.ProveedorID

JOIN

    UnidadesMedida u ON p.UnidadMedidaID = u.UnidadMedidaID

JOIN

    EstadosProductos e ON p.EstadoProductoID = e.EstadoProductoID;



-- Consultar movimientos de inventario actualizados por almacén

SELECT

    m.MovimientoID,

    p.Nombre AS NombreProducto,

    m.Fecha,

    m.TipoMovimiento,

    m.Cantidad,

    a.Nombre AS NombreAlmacen,

    m.Comentarios

FROM

    MovimientosInventario m

JOIN

    Productos p ON m.ProductoID = p.ProductoID

JOIN

    Almacenes a ON m.AlmacenID = a.AlmacenID

ORDER BY

    m.Fecha DESC;

</sql><current_tab id="1"/></tab_sql></sqlb_project>
