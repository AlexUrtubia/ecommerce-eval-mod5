package com.bootcamp.java.modulo5.ecommerce_eval_mod5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.java.modulo5.ecommerce_eval_mod5.models.CarritoCompra;
import com.bootcamp.java.modulo5.ecommerce_eval_mod5.models.ProductoCarrito;
import com.bootcamp.java.modulo5.ecommerce_eval_mod5.service.CarritoCompraService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/carritos")
public class CarritoCompraController {

	private final CarritoCompraService carritoCompraService;

	@Autowired
	public CarritoCompraController(CarritoCompraService carritoCompraService) {
		this.carritoCompraService = carritoCompraService;
	}

	@Operation(summary = "Agregar un nuevo carrito")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Carrito creado exitosamente", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CarritoCompra.class))) })
	@PostMapping
	public ResponseEntity<CarritoCompra> agregarCarrito(@RequestBody CarritoCompra carritoCompra) {
		CarritoCompra nuevoCarrito = carritoCompraService.agregarCarrito(carritoCompra);
		return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCarrito);
	}

	@Operation(summary = "Obtener todos los carritos")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Lista de carritos obtenida", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CarritoCompra.class))),
			@ApiResponse(responseCode = "404", description = "No se encontraron carritos", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = "[]"))) })
	@GetMapping
	public ResponseEntity<List<CarritoCompra>> obtenerTodosLosCarritos() {
		List<CarritoCompra> carritos = carritoCompraService.obtenerTodosLosCarritos();
		if (carritos.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
		}
		return ResponseEntity.ok(carritos);
	}

	@Operation(summary = "Obtener un producto dentro de un carrito por ID")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Producto encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProductoCarrito.class))),
			@ApiResponse(responseCode = "404", description = "Carrito o producto no encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = "{\"error\": \"Producto no encontrado en el carrito\"}"))) })
	@GetMapping("/productoById/{carritoId}")
	public ResponseEntity<ProductoCarrito> productoCarritoPorId(@PathVariable String carritoId,
			@RequestParam(value = "productoId") Integer productoId) {
		ProductoCarrito producto = carritoCompraService.buscarProductoCarritoPorId(carritoId, productoId);
		return ResponseEntity.ok(producto);
	}

	@Operation(summary = "Buscar un carrito por ID de usuario")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Carrito encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CarritoCompra.class))),
			@ApiResponse(responseCode = "404", description = "Carrito no encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = "{\"error\": \"Carrito no encontrado para el usuario\"}"))) })
	@GetMapping("/{idUsuario}")
	public ResponseEntity<CarritoCompra> buscarCarritoPorIdUsuario(@PathVariable String idUsuario) {
		CarritoCompra carrito = carritoCompraService.buscarCarritoPorIdUsuario(idUsuario);
		return ResponseEntity.ok(carrito);
	}
}
