package com.bootcamp.java.modulo5.ecommerce_eval_mod5.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bootcamp.java.modulo5.ecommerce_eval_mod5.exception.ProductoByIdNoEncontrado;
import com.bootcamp.java.modulo5.ecommerce_eval_mod5.models.Producto;
import com.bootcamp.java.modulo5.ecommerce_eval_mod5.repository.postgresql.ProductoRepository;

class ProductoServiceTest {

	@InjectMocks
	private ProductoService productoService;

	@Mock
	private ProductoRepository productoRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testListarProductos() {
		List<Producto> lista = new ArrayList<>();
		lista.add(new Producto(null, "COD1", BigDecimal.valueOf(100), 10, "Producto1"));
		lista.add(new Producto(null, "COD2", BigDecimal.valueOf(200), 5, "Producto2"));

		when(productoRepository.findAll()).thenReturn(lista);

		List<Producto> result = productoService.listarProductos();

		assertNotNull(result);
		assertEquals(2, result.size());
		verify(productoRepository, times(1)).findAll();
	}
	
	@Test
    public void testBuscarProductoPorId_Found() {
        Producto producto = new Producto(1, "COD1", BigDecimal.valueOf(100), 10, "Producto1");
        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));
        
        Producto result = productoService.buscarProductoPorId(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(productoRepository, times(1)).findById(1);
    }
	
    @Test
    public void testBuscarProductoPorId_NotFound() {
        when(productoRepository.findById(1)).thenReturn(Optional.empty());
        
        assertThrows(ProductoByIdNoEncontrado.class, () -> productoService.buscarProductoPorId(1));
        verify(productoRepository, times(1)).findById(1);
    }
    
    @Test
    public void testCrearProducto_Null() {
        assertThrows(IllegalArgumentException.class, () -> productoService.crearProducto(null));
        verify(productoRepository, never()).save(any());
    }
    
    @Test
    public void testCrearProducto_WithId() {
        Producto producto = new Producto(1, "COD1", BigDecimal.valueOf(100), 10, "Producto1");
        assertThrows(IllegalArgumentException.class, () -> productoService.crearProducto(producto));
        verify(productoRepository, never()).save(any());
    }
    
    @Test
    public void testActualizarProducto_ValidUpdates() {
        // Creamos un producto de ejemplo.
        Producto producto = new Producto(1, "COD1", BigDecimal.valueOf(100), 10, "Producto1");
        
        // Configuramos el repositorio para que al buscar el producto por ID, retorne el producto de ejemplo.
        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));
        // Configuramos el repositorio para que al guardar, retorne el producto actualizado.
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        
        // Definimos un mapa de actualizaciones válidas.
        Map<String, Object> updates = Map.of(
            "codigoProducto", "COD2",
            "precioUnitario", BigDecimal.valueOf(150),
            "stock", 8,
            "nombre", "Producto Actualizado"
        );
        
        // Ejecutamos el método a probar.
        Producto result = productoService.actualizarProducto(1, updates);
        
        // Verificamos que el producto se haya actualizado correctamente.
        assertNotNull(result);
        assertEquals("COD2", result.getCodigoProducto());
        assertEquals(BigDecimal.valueOf(150), result.getPrecioUnitario());
        assertEquals(8, result.getStock());
        assertEquals("Producto Actualizado", result.getNombre());
        
        // Verificamos que se llamó al repositorio para buscar y guardar el producto.
        verify(productoRepository, times(1)).findById(1);
        verify(productoRepository, times(1)).save(producto);
    }
    
    @Test
    public void testGuardarProducto() {
        Producto producto = new Producto(1, "COD1", BigDecimal.valueOf(100), 10, "Producto1");
        when(productoRepository.save(producto)).thenReturn(producto);
        
        Producto result = productoService.guardarProducto(producto);
        
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(productoRepository, times(1)).save(producto);
    }
    
    @Test
    public void testEliminarProducto() {
        doNothing().when(productoRepository).deleteById(1);
        
        productoService.eliminarProducto(1);
        
        verify(productoRepository, times(1)).deleteById(1);
    }

}
