package com.bootcamp.java.modulo5.ecommerce_eval_mod5.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bootcamp.java.modulo5.ecommerce_eval_mod5.enums.EstadoPedido;
import com.bootcamp.java.modulo5.ecommerce_eval_mod5.exception.CarritoNoEncontradoException;
import com.bootcamp.java.modulo5.ecommerce_eval_mod5.exception.CarritoVacioException;
import com.bootcamp.java.modulo5.ecommerce_eval_mod5.exception.OrdenCompraNotFoundException;
import com.bootcamp.java.modulo5.ecommerce_eval_mod5.models.CarritoCompra;
import com.bootcamp.java.modulo5.ecommerce_eval_mod5.models.DetalleCompra;
import com.bootcamp.java.modulo5.ecommerce_eval_mod5.models.OrdenCompra;
import com.bootcamp.java.modulo5.ecommerce_eval_mod5.models.Producto;
import com.bootcamp.java.modulo5.ecommerce_eval_mod5.models.ProductoCarrito;
import com.bootcamp.java.modulo5.ecommerce_eval_mod5.repository.postgresql.OrdenCompraRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * Servicio para gestionar las operaciones de OrdenCompra.
 * <p>
 * Este servicio se encarga de generar órdenes de compra a partir del carrito de un usuario,
 * calcular totales, buscar órdenes y marcar órdenes como entregadas. Las operaciones críticas
 * se ejecutan dentro de una transacción para garantizar la integridad de los datos.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class OrdenCompraService {
    
    private final OrdenCompraRepository ordenCompraRepository;
    private final CarritoCompraService carritoCompraService;
    private final ProductoService productoService;
    
    /**
     * Calcula el total de la compra sumando el total de cada detalle.
     *
     * @param detalles la lista de detalles de compra.
     * @return el total de la compra como {@code BigDecimal}.
     */
    public BigDecimal calcularTotalCompra(List<DetalleCompra> detalles) {
        return detalles.stream()
                .map(DetalleCompra::getTotalDetalle)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    /**
     * Busca y retorna todas las órdenes de compra registradas.
     *
     * @return una lista de {@code OrdenCompra}.
     */
    public List<OrdenCompra> buscarTodasLasOrdenes() {
        return ordenCompraRepository.findAll();
    }
    
    /**
     * Busca una orden de compra por su identificador.
     *
     * @param id el identificador de la orden de compra.
     * @return la {@code OrdenCompra} encontrada.
     * @throws OrdenCompraNotFoundException si no se encuentra la orden con el id proporcionado.
     */
    public OrdenCompra buscarOrdenPorId(Integer id) {
        return ordenCompraRepository.findById(id)
                .orElseThrow(() -> new OrdenCompraNotFoundException(id));
    }
    
    /**
     * Genera una nueva orden de compra a partir del carrito de un usuario.
     * <p>
     * El flujo de este método es:
     * <ul>
     *   <li>Obtener el carrito del usuario y verificar que no esté vacío.</li>
     *   <li>Crear la orden de compra y asignar el estado inicial como {@code PENDIENTE}.</li>
     *   <li>Asignar la fecha de emisión a la fecha actual y establecer la fecha solicitada a 3 días después.</li>
     *   <li>Procesar cada producto del carrito: buscar el producto, validar y reducir stock, y crear un detalle de compra.</li>
     *   <li>Asignar la lista de detalles y calcular el precio total de la orden.</li>
     *   <li>Guardar la orden de compra y, opcionalmente, eliminar el carrito.</li>
     * </ul>
     * </p>
     *
     * @param idUsuario el identificador del usuario.
     * @return la {@code OrdenCompra} generada.
     * @throws CarritoVacioException si el carrito está vacío.
     * @throws CarritoNoEncontradoException si no se encuentra el carrito para el usuario.
     */
    @Transactional
    public OrdenCompra generarOrdenDeCompra(String idUsuario) {
        // 1. Obtener el carrito del usuario y comprobar que no esté vacío
        CarritoCompra carrito = carritoCompraService.buscarCarritoPorIdUsuario(idUsuario);
        List<ProductoCarrito> productosCarrito = carrito.getProductos();
        
        if (productosCarrito.isEmpty()) {
            throw new CarritoVacioException();
        }
        
        // 2. Crear la orden y asignar el estado inicial como PENDIENTE
        OrdenCompra ordenCompra = new OrdenCompra();
        ordenCompra.setIdUsuario(idUsuario);
        ordenCompra.setEstado(EstadoPedido.PENDIENTE);
        
        // Asignar la fecha de emisión actual
        Date fechaEmision = new Date(System.currentTimeMillis());
        ordenCompra.setFechaEmision(fechaEmision);
        
        // Establecer fechaSolicitada a 3 días después de la fecha de emisión
        long tresDiasEnMilisegundos = 3L * 24 * 60 * 60 * 1000;
        ordenCompra.setFechaSolicitada(new Date(fechaEmision.getTime() + tresDiasEnMilisegundos));
        
        // 3. Procesar cada producto del carrito
        List<DetalleCompra> detalles = new ArrayList<>();
        for (ProductoCarrito prodCarrito : productosCarrito) {
            // Buscar y validar existencia del producto
            Producto producto = productoService.buscarProductoPorId(prodCarrito.getId());
            
            // Validar y reducir stock del producto, luego guardarlo
            producto.reducirStock(prodCarrito.getCantidad());
            productoService.guardarProducto(producto);
            
            // Crear detalle de compra
            DetalleCompra detalleCompra = new DetalleCompra();
            detalleCompra.setOrdenCompra(ordenCompra);
            detalleCompra.setCodigoProducto(prodCarrito.getCodigoProducto());
            detalleCompra.setCantidad(prodCarrito.getCantidad());
            detalleCompra.setTotalDetalle(
                producto.getPrecioUnitario().multiply(BigDecimal.valueOf(prodCarrito.getCantidad()))
            );
            detalles.add(detalleCompra);
        }
        
        // 4. Asignar detalles de compra y calcular el total de la orden
        ordenCompra.setDetalles(detalles);
        ordenCompra.setPrecioTotalCompra(calcularTotalCompra(detalles));
        
        // 5. Guardar la orden
        ordenCompraRepository.save(ordenCompra);
        // Opcional: carritoService.eliminarCarrito(carrito);
        
        return ordenCompra;
    }
    
    /**
     * Marca una orden de compra como entregada.
     * <p>
     * Este método actualiza la orden estableciendo la fecha de entrega a la fecha actual
     * y cambia el estado a {@code ENTREGADO}.
     * </p>
     *
     * @param idOrden el identificador de la orden a actualizar.
     * @return la {@code OrdenCompra} actualizada.
     * @throws OrdenCompraNotFoundException si no se encuentra la orden con el id proporcionado.
     */
    @Transactional
    public OrdenCompra marcarOrdenComoEntregada(Integer idOrden) {
        OrdenCompra orden = ordenCompraRepository.findById(idOrden)
                .orElseThrow(() -> new OrdenCompraNotFoundException(idOrden));
        // Asignar la fecha de entrega a la fecha actual
        orden.setFechaEntrega(new Date(System.currentTimeMillis()));
        // Cambiar el estado a ENTREGADO
        orden.setEstado(EstadoPedido.ENTREGADO);
        return ordenCompraRepository.save(orden);
    }
}
