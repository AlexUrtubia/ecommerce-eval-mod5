package com.bootcamp.java.modulo5.ecommerce_eval_mod5.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bootcamp.java.modulo5.ecommerce_eval_mod5.models.OrdenCompra;
import com.bootcamp.java.modulo5.ecommerce_eval_mod5.service.OrdenCompraService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ordenCompra")
@RequiredArgsConstructor
public class OrdenCompraController {

	private final OrdenCompraService ordenCompraService;

	@Operation(summary = "Obtener todas las órdenes de compra")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Órdenes obtenidas exitosamente", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrdenCompra.class))),
			@ApiResponse(responseCode = "204", description = "No se encontraron órdenes", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = "[]"))) })
	@GetMapping
	public ResponseEntity<List<OrdenCompra>> buscarTodasLasOrdenes() {
		List<OrdenCompra> ordenes = ordenCompraService.buscarTodasLasOrdenes();
		if (ordenes.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(ordenes);
	}

	@Operation(summary = "Buscar una orden por ID")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Orden encontrada", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrdenCompra.class))),
			@ApiResponse(responseCode = "404", description = "Orden no encontrada", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = "{\"error\": \"Orden no encontrada\"}"))) })
	@GetMapping("/{id}")
	public ResponseEntity<OrdenCompra> buscarOrdenPorId(@PathVariable Integer id) {
		OrdenCompra orden = ordenCompraService.buscarOrdenPorId(id);
		return ResponseEntity.ok(orden);
	}

	@Operation(summary = "Generar una orden de compra para un usuario")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Orden creada exitosamente", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrdenCompra.class))),
			@ApiResponse(responseCode = "400", description = "Error en la solicitud", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = "{\"error\": \"No se pudo generar la orden\"}"))) })
	@PostMapping("/{idUsuario}")
	public ResponseEntity<OrdenCompra> generarOrden(@PathVariable String idUsuario) {
		OrdenCompra ordenCompra = ordenCompraService.generarOrdenDeCompra(idUsuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(ordenCompra);
	}

	@Operation(summary = "Marcar una orden como entregada")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Orden marcada como entregada", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrdenCompra.class))),
			@ApiResponse(responseCode = "404", description = "Orden no encontrada", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = "{\"error\": \"Orden no encontrada\"}"))) })
	@PatchMapping("/entregar/{idOrden}")
	public ResponseEntity<OrdenCompra> marcarOrdenComoEntregada(@PathVariable Integer idOrden) {
		OrdenCompra ordenActualizada = ordenCompraService.marcarOrdenComoEntregada(idOrden);
		return ResponseEntity.ok(ordenActualizada);
	}
}
