package com.bootcamp.java.modulo5.ecommerce_eval_mod5.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootcamp.java.modulo5.ecommerce_eval_mod5.exception.ProductoByIdNoEncontrado;
import com.bootcamp.java.modulo5.ecommerce_eval_mod5.models.Producto;
import com.bootcamp.java.modulo5.ecommerce_eval_mod5.repository.postgresql.ProductoRepository;

import jakarta.transaction.Transactional;

/**
 * Servicio para gestionar operaciones relacionadas con los productos.
 */
@Service
public class ProductoService {

	@Autowired
	private ProductoRepository productoRepository;

	/**
	 * Lista todos los productos disponibles.
	 * 
	 * @return Lista de productos.
	 */
	public List<Producto> listarProductos() {
		return productoRepository.findAll();
	}

	/**
	 * Busca un producto por su ID.
	 * 
	 * @param id Identificador del producto.
	 * @return Producto encontrado.
	 * @throws ProductoByIdNoEncontrado si el producto no existe.
	 */
	public Producto buscarProductoPorId(Integer id) {
		return productoRepository.findById(id).orElseThrow(() -> new ProductoByIdNoEncontrado(id));
	}

	/**
	 * Crea un nuevo producto.
	 * 
	 * @param producto Producto a crear.
	 * @return Producto creado.
	 * @throws IllegalArgumentException si el producto es nulo o ya tiene un ID.
	 */
	public Producto crearProducto(Producto producto) {
		if (producto == null) {
			throw new IllegalArgumentException("El producto no puede ser null");
		}
		if (producto.getId() != null) {
			throw new IllegalArgumentException("El ID debe ser generado automáticamente");
		}
		return productoRepository.save(producto);
	}

	/**
	 * Actualiza los datos de un producto existente.
	 * 
	 * @param id      ID del producto a actualizar.
	 * @param updates Mapa con los campos y valores a modificar.
	 * @return Producto actualizado.
	 * @throws ProductoByIdNoEncontrado si el producto no existe.
	 * @throws RuntimeException         si algún campo no es permitido.
	 */
	@Transactional
	public Producto actualizarProducto(Integer id, Map<String, Object> updates) {
		Producto producto = productoRepository.findById(id).orElseThrow(() -> new ProductoByIdNoEncontrado(id));

		updates.forEach((campo, valor) -> {
			switch (campo) {
			case "codigoProducto" -> {
				if (valor instanceof String) {
					producto.setCodigoProducto((String) valor);
				}
			}
			case "precioUnitario" -> {
				if (valor instanceof BigDecimal) {
					producto.setPrecioUnitario((BigDecimal) valor);
				}
			}
			case "stock" -> {
				if (valor instanceof Integer) {
					producto.setStock((Integer) valor);
				}
			}
			case "nombre" -> {
				if (valor instanceof String) {
					producto.setNombre((String) valor);
				}
			}
			default -> throw new RuntimeException("Campo no permitido: " + campo);
			}
		});
		return productoRepository.save(producto);
	}

	/**
	 * Guarda o actualiza un producto en la base de datos.
	 * 
	 * @param producto Producto a guardar.
	 * @return Producto guardado.
	 */
	public Producto guardarProducto(Producto producto) {
		return productoRepository.save(producto);
	}

	/**
	 * Elimina un producto por su ID.
	 * 
	 * @param id Identificador del producto a eliminar.
	 */
	public void eliminarProducto(Integer id) {
		productoRepository.deleteById(id);
	}
}
