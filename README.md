# E-Commerce REST API

Este proyecto es un sistema REST que permite la interacción con dos bases de datos diferentes:
MongoDB y PostgreSQL. Su objetivo es gestionar carritos de compra en MongoDB y realizar
transacciones de compra en PostgreSQL.

## Tecnologías utilizadas
- **Spring Boot**
- **MongoDB** (para la gestión de carritos de compra)
- **PostgreSQL** (para la gestión de transacciones y órdenes de compra)
- **JPA y Hibernate**
- **Swagger** (para la documentación de la API)
- **JUnit y Mockito** (para pruebas unitarias)

## Arquitectura del Proyecto
El sistema está organizado en capas:
- **Capa Modelo**: Define las entidades y documentos de la base de datos.
- **Capa Repositorio**: Interfaz con la base de datos.
- **Capa Servicio**: Contiene la lógica de negocio.
- **Capa Controlador**: Define los endpoints de la API REST.

## Esquemas de Base de Datos

### MongoDB (Carrito de Compra)
Colección: **CarritoCompra**
```json
{
  "idUsuario": "String",
  "productos": [
    {
      "id": "Integer",
      "codigoProducto": "String"
    }
  ]
}
```

### PostgreSQL (Gestión de Compras)
- **Producto**
  - id (Integer, PK)
  - codigo_producto (String, único)
  - precio_unitario (Integer)
  - stock (Integer)

- **OrdenCompra**
  - id (Integer, PK)
  - fecha_emision (Date)
  - fecha_entrega (Date)
  - fecha_solicitada (Date)

- **DetalleCompra**
  - id (Integer, PK)
  - codigo_producto (String, FK a Producto)
  - cantidad (Integer)
  - total_detalle (Integer)

## Endpoints

### CarritoCompraController
- **POST /carrito** - Agregar un carrito de compra
- **GET /carrito/{idUsuario}** - Obtener carrito de compra por usuario

### CompraController
- **POST /compra** - Generar una compra desde un carrito

## Ejecución del Proyecto
### Prerrequisitos
- Java 17+
- PostgreSQL y MongoDB en ejecución
- Postman o cualquier herramienta para probar APIs

### Configuración
1. Clonar el repositorio y navegar a la carpeta del proyecto.
2. Configurar las credenciales de MongoDB y PostgreSQL en `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_password
   spring.data.mongodb.uri=mongodb://localhost:27017/ecommerce
   ```
3. Ejecutar el proyecto con:
   ```sh
   mvn spring-boot:run
   ```
4. Acceder a la documentación de la API en `http://localhost:8080/swagger-ui.html`.

### Pruebas
Para ejecutar pruebas unitarias:
```sh
mvn test
```

## Documentación
El proyecto incluye documentación con:
- **Javadoc** para describir clases y métodos.
- **Swagger** para visualizar y probar los endpoints.

---
Este proyecto demuestra el uso de múltiples bases de datos en una aplicación Spring Boot,
incluyendo pruebas y documentación adecuada para facilitar su mantenimiento y escalabilidad.

