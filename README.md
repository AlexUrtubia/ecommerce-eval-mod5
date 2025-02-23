# Proyecto E-commerce con Spring Boot, PostgreSQL y MongoDB

Este proyecto implementa un sistema RESTful que permite la gestión de carritos de compra en MongoDB y la realización de transacciones de compra en PostgreSQL. Se incluyen pruebas unitarias, documentación con Javadoc y documentación de endpoints con Swagger.

## Tecnologías Utilizadas
- **Spring Boot** (Spring Data, Spring Web, Spring Boot Starter Test)
- **MongoDB** (para la gestión de carritos de compra)
- **PostgreSQL** (para el registro de transacciones de compra)
- **Swagger** (para la documentación de la API)
- **JUnit y Mockito** (para pruebas unitarias)

## Esquema de Bases de Datos

### MongoDB (Colección `User` - Carrito de Compra)
- **CarritoCompra**:
  - `id`: String (Identificador del carrito)
  - `idUsuario`: String (Identificador del usuario)
  - `productos`: Lista de `ProductoCarrito`

### PostgreSQL (Tablas para la gestión de compras)

#### Tabla `productos`
- **Producto**:
  - `id`: Integer (Identificador único)
  - `codigoProducto`: String (Código único del producto)
  - `precioUnitario`: Decimal (Precio unitario del producto)
  - `stock`: Integer (Cantidad disponible en stock)
  - `nombre`: String (Nombre del producto)

#### Tabla `ordenes_compra`
- **OrdenCompra**:
  - `id`: Integer (Identificador único de la orden)
  - `idUsuario`: String (Identificador del usuario que realiza la compra)
  - `precioTotalCompra`: Decimal (Monto total de la compra)
  - `fechaEmision`: Date (Fecha de emisión de la orden)
  - `fechaEntrega`: Date (Fecha estimada de entrega)
  - `fechaSolicitada`: Date (Fecha solicitada por el usuario)
  - `estado`: Enum (Estado de la orden: PENDIENTE, EN_PROCESO, COMPLETADA, CANCELADA)
  - `detalles`: Lista de `DetalleCompra`

#### Tabla `detalles_compra`
- **DetalleCompra**:
  - `id`: Integer (Identificador único del detalle de compra)
  - `codigoProducto`: String (Código del producto comprado)
  - `cantidad`: Integer (Cantidad comprada del producto)
  - `totalDetalle`: Decimal (Monto total por el producto en la compra)
  - `ordenCompra`: Referencia a `OrdenCompra`

#### Objeto `ProductoCarrito`
- **ProductoCarrito**:
  - `id`: Integer (Identificador del producto en el carrito)
  - `codigoProducto`: String (Código del producto)
  - `cantidad`: Integer (Cantidad agregada al carrito)

## Endpoints de la API

### Productos
- `POST /api/productos` → Crear un producto.
- `GET /api/productos` → Consultar todos los productos.
- `GET /api/productos/{productoId}` → Consultar producto por ID.

### Carrito de Compra (MongoDB)
- `POST /api/carritos` → Crear un carrito de compra.
- `GET /api/carritos` → Consultar todos los carritos.
- `GET /api/carritos/{usuarioId}` → Obtener el carrito de compra de un usuario.
- `GET /api/carritos/productoById/{usuarioId}?productoId={productoId}` → Obtener un producto dentro del carrito de un usuario.

### Gestión de Compras (PostgreSQL)
- `POST /api/ordenCompra` → Generar una orden de compra.
- `GET /api/ordenCompra` → Consultar todas las órdenes de compra.
- `GET /api/ordenCompra/{ordenCompraId}` → Consultar una orden de compra por ID.
- `PATCH /api/productos/entregar/{ordenCompraId}` → Marcar una orden de compra como entregada.

## Ejecución Local del Proyecto

### Requisitos Previos
- Java 17+
- Maven
- MongoDB en ejecución
- PostgreSQL en ejecución con las tablas configuradas

### Pasos para la Ejecución
1. Clonar el repositorio.
2. Configurar las credenciales de MongoDB y PostgreSQL en `application.properties`.
3. Ejecutar el proyecto con:
   ```sh
   mvn spring-boot:run
   ```
4. Importar la colección de Postman (si se proporciona) o probar los endpoints directamente.

## Documentación y Pruebas
- Swagger UI disponible en: `http://localhost:8081/swagger-ui.html`
- Pruebas unitarias ejecutables con:
   ```sh
   mvn test
   ```

