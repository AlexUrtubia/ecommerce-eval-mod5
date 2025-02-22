package com.bootcamp.java.modulo5.ecommerce_eval_mod5.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.bootcamp.java.modulo5.ecommerce_eval_mod5.exception.OrdenCompraNotFoundException;
import com.bootcamp.java.modulo5.ecommerce_eval_mod5.models.DetalleCompra;
import com.bootcamp.java.modulo5.ecommerce_eval_mod5.models.OrdenCompra;
import com.bootcamp.java.modulo5.ecommerce_eval_mod5.repository.postgresql.OrdenCompraRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class OrdenCompraServiceTest {
	 	@InjectMocks 
	    private OrdenCompraService ordenCompraService;
	    
	    @Mock
	    private OrdenCompraRepository ordenCompraRepository;
	    
	    @Mock
	    private CarritoCompraService carritoCompraService;
	    
	    @Mock
	    private ProductoService productoService;
	    
	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);
	        ordenCompraService = new OrdenCompraService(ordenCompraRepository, carritoCompraService, productoService);
	    }
	    
	    // Test para calcularTotalCompra
	    @Test
	    public void testCalcularTotalCompra() {
	        DetalleCompra detalle1 = new DetalleCompra();
	        detalle1.setTotalDetalle(BigDecimal.valueOf(100));
	        
	        DetalleCompra detalle2 = new DetalleCompra();
	        detalle2.setTotalDetalle(BigDecimal.valueOf(200));
	        
	        List<DetalleCompra> detalles = List.of(detalle1, detalle2);
	        
	        BigDecimal total = ordenCompraService.calcularTotalCompra(detalles);
	        assertEquals(BigDecimal.valueOf(300), total);
	    }
	    
	    // Test para buscarTodasLasOrdenes
	    @Test
	    public void testBuscarTodasLasOrdenes() {
	        List<OrdenCompra> ordenes = new ArrayList<>();
	        OrdenCompra o1 = new OrdenCompra();
	        o1.setIdUsuario("user1");
	        OrdenCompra o2 = new OrdenCompra();
	        o2.setIdUsuario("user2");
	        ordenes.add(o1);
	        ordenes.add(o2);
	        
	        when(ordenCompraRepository.findAll()).thenReturn(ordenes);
	        
	        List<OrdenCompra> result = ordenCompraService.buscarTodasLasOrdenes();
	        assertNotNull(result);
	        assertEquals(2, result.size());
	        verify(ordenCompraRepository, times(1)).findAll();
	    }
	    
	    // Test para buscarOrdenPorId (caso encontrado)
	    @Test
	    public void testBuscarOrdenPorId_Found() {
	        OrdenCompra orden = new OrdenCompra();
	        orden.setId(1);
	        orden.setIdUsuario("user1");
	        
	        when(ordenCompraRepository.findById(1)).thenReturn(Optional.of(orden));
	        
	        OrdenCompra result = ordenCompraService.buscarOrdenPorId(1);
	        assertNotNull(result);
	        assertEquals(1, result.getId());
	        verify(ordenCompraRepository, times(1)).findById(1);
	    }
	    
	    // Test para buscarOrdenPorId (no encontrada)
	    @Test
	    public void testBuscarOrdenPorId_NotFound() {
	        when(ordenCompraRepository.findById(1)).thenReturn(Optional.empty());
	        assertThrows(OrdenCompraNotFoundException.class, () -> ordenCompraService.buscarOrdenPorId(1));
	        verify(ordenCompraRepository, times(1)).findById(1);
	    }
}
