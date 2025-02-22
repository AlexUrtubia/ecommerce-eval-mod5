package com.bootcamp.java.modulo5.ecommerce_eval_mod5.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.bootcamp.java.modulo5.ecommerce_eval_mod5.exception.CarritoNoEncontradoException;
import com.bootcamp.java.modulo5.ecommerce_eval_mod5.exception.ProductoCarritoByIdNoEncontradoException;
import com.bootcamp.java.modulo5.ecommerce_eval_mod5.models.CarritoCompra;
import com.bootcamp.java.modulo5.ecommerce_eval_mod5.models.ProductoCarrito;
import com.bootcamp.java.modulo5.ecommerce_eval_mod5.repository.mongodb.CarritoCompraRepository;

@SpringBootTest
public class CarritoCompraServiceTest {
	@InjectMocks
	private CarritoCompraService carritoCompraService;

	@Mock
	private CarritoCompraRepository carritoCompraRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Test que verifica que agregarCarrito() retorne el carrito guardado.
	 */
	@Test
	public void testAgregarCarrito() {
		CarritoCompra carrito = new CarritoCompra();
		carrito.setIdUsuario("user1");

		when(carritoCompraRepository.save(carrito)).thenReturn(carrito);

		CarritoCompra result = carritoCompraService.agregarCarrito(carrito);
		assertNotNull(result);
		assertEquals("user1", result.getIdUsuario());
		verify(carritoCompraRepository, times(1)).save(carrito);
	}

	/**
	 * Test que verifica buscarCarritoPorIdUsuario() cuando el carrito existe.
	 */
	@Test
	public void testBuscarCarritoPorIdUsuario_Found() {
		CarritoCompra carrito = new CarritoCompra();
		carrito.setIdUsuario("user1");

		when(carritoCompraRepository.findByIdUsuario("user1")).thenReturn(Optional.of(carrito));

		CarritoCompra result = carritoCompraService.buscarCarritoPorIdUsuario("user1");
		assertNotNull(result);
		assertEquals("user1", result.getIdUsuario());
		verify(carritoCompraRepository, times(1)).findByIdUsuario("user1");
	}

	/**
	 * Test que verifica buscarCarritoPorIdUsuario() cuando el carrito no existe.
	 */
	@Test
	public void testBuscarCarritoPorIdUsuario_NotFound() {
		when(carritoCompraRepository.findByIdUsuario("user1")).thenReturn(Optional.empty());
		assertThrows(CarritoNoEncontradoException.class, () -> carritoCompraService.buscarCarritoPorIdUsuario("user1"));
		verify(carritoCompraRepository, times(1)).findByIdUsuario("user1");
	}

	/**
	 * Test que verifica buscarProductoCarritoPorId() cuando el producto existe en
	 * el carrito.
	 */
	@Test
	public void testBuscarProductoCarritoPorId_Found() {
		// Creamos un ProductoCarrito con id 100 y código \"P100\".
		ProductoCarrito productoCarrito = new ProductoCarrito();
		productoCarrito.setId(100);
		productoCarrito.setCodigoProducto("P100");

		List<ProductoCarrito> productos = new ArrayList<>();
		productos.add(productoCarrito);

		CarritoCompra carrito = new CarritoCompra();
		carrito.setIdUsuario("user1");
		carrito.setProductos(productos);

		when(carritoCompraRepository.findByIdUsuario("user1")).thenReturn(Optional.of(carrito));

		ProductoCarrito result = carritoCompraService.buscarProductoCarritoPorId("user1", 100);
		assertNotNull(result);
		assertEquals(100, result.getId());
		assertEquals("P100", result.getCodigoProducto());
		verify(carritoCompraRepository, times(1)).findByIdUsuario("user1");
	}

	/**
	 * Test que verifica buscarProductoCarritoPorId() cuando el producto no se
	 * encuentra en el carrito.
	 */
	@Test
	public void testBuscarProductoCarritoPorId_ProductNotFound() {
		// El carrito contiene solo un producto con id 100.
		ProductoCarrito productoCarrito = new ProductoCarrito();
		productoCarrito.setId(100);
		productoCarrito.setCodigoProducto("P100");

		List<ProductoCarrito> productos = new ArrayList<>();
		productos.add(productoCarrito);

		CarritoCompra carrito = new CarritoCompra();
		carrito.setIdUsuario("user1");
		carrito.setProductos(productos);

		when(carritoCompraRepository.findByIdUsuario("user1")).thenReturn(Optional.of(carrito));

		assertThrows(ProductoCarritoByIdNoEncontradoException.class,
				() -> carritoCompraService.buscarProductoCarritoPorId("user1", 200));
		verify(carritoCompraRepository, times(1)).findByIdUsuario("user1");
	}

	/**
	 * Test que verifica que obtenerTodosLosCarritos() retorne la lista correcta de
	 * carritos.
	 */
	@Test
	public void testObtenerTodosLosCarritos() {
		List<CarritoCompra> carritos = new ArrayList<>();
		CarritoCompra carrito1 = new CarritoCompra();
		carrito1.setIdUsuario("user1");
		CarritoCompra carrito2 = new CarritoCompra();
		carrito2.setIdUsuario("user2");
		carritos.add(carrito1);
		carritos.add(carrito2);

		when(carritoCompraRepository.findAll()).thenReturn(carritos);

		List<CarritoCompra> result = carritoCompraService.obtenerTodosLosCarritos();
		assertNotNull(result);
		assertEquals(2, result.size());
		verify(carritoCompraRepository, times(1)).findAll();
	}
}
