package com.bootcamp.java.modulo5.ecommerce_eval_mod5.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bootcamp.java.modulo5.ecommerce_eval_mod5.models.Producto;
import com.bootcamp.java.modulo5.ecommerce_eval_mod5.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

	@Autowired
	private ProductoService productoService;

	@Operation(summary = "Listar todos los productos")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Lista de productos obtenida", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Producto.class))),
			@ApiResponse(responseCode = "404", description = "No se encontraron productos", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = "[]"))) })
	@GetMapping
	public ResponseEntity<List<Producto>> listarProductos() {
		List<Producto> productos = productoService.listarProductos();
		if (productos == null || productos.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
		}
		return ResponseEntity.ok(productos);
	}

	@Operation(summary = "Obtener un producto por ID")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Producto encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Producto.class))),
			@ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = "{\"error\": \"Producto no encontrado\"}"))) })
	@GetMapping("/{id}")
	public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Integer id) {
		Producto producto = productoService.buscarProductoPorId(id);
		if (producto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(producto);
	}

	@Operation(summary = "Crear un nuevo producto")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Producto creado exitosamente", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Producto.class))) })
	@PostMapping
	public ResponseEntity<Producto> crearProductos(@RequestBody Producto producto) {
		Producto nuevoProducto = productoService.crearProducto(producto);
		return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
	}

	@Operation(summary = "Actualizar un producto parcialmente")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Producto.class))),
			@ApiResponse(responseCode = "400", description = "Datos de actualización inválidos", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = "{\"error\": \"Datos de actualización inválidos\"}"))),
			@ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = "{\"error\": \"Producto no encontrado\"}"))) })
	@PatchMapping("/{id}")
	public ResponseEntity<Producto> actualizarProductos(@PathVariable Integer id,
			@RequestBody Map<String, Object> updates) {
		Producto productoActualizado = productoService.actualizarProducto(id, updates);
		if (productoActualizado == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(productoActualizado);
	}
}
