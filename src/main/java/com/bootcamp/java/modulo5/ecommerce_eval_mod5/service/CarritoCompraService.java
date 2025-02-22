package com.bootcamp.java.modulo5.ecommerce_eval_mod5.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootcamp.java.modulo5.ecommerce_eval_mod5.exception.CarritoNoEncontradoException;
import com.bootcamp.java.modulo5.ecommerce_eval_mod5.exception.ProductoCarritoByIdNoEncontradoException;
import com.bootcamp.java.modulo5.ecommerce_eval_mod5.models.CarritoCompra;
import com.bootcamp.java.modulo5.ecommerce_eval_mod5.models.ProductoCarrito;
import com.bootcamp.java.modulo5.ecommerce_eval_mod5.repository.mongodb.CarritoCompraRepository;

/**
 * Servicio para gestionar operaciones relacionadas con el carrito de compras.
 */
@Service
public class CarritoCompraService {

    private final CarritoCompraRepository carritoCompraRepository;

    @Autowired
    public CarritoCompraService(CarritoCompraRepository carritoCompraRepository) {
        this.carritoCompraRepository = carritoCompraRepository;
    }

    /**
     * Agrega un nuevo carrito de compras.
     * @param carritoCompra Carrito a agregar.
     * @return Carrito agregado.
     */
    
    public CarritoCompra agregarCarrito(CarritoCompra carritoCompra) {
        return carritoCompraRepository.save(carritoCompra);
    }

    /**
     * Busca un carrito de compras por el ID del usuario.
     * @param idUsuario Identificador del usuario.
     * @return Carrito encontrado.
     * @throws CarritoNoEncontradoException si el carrito no existe.
     */
    
    public CarritoCompra buscarCarritoPorIdUsuario(String idUsuario) {
        System.out.println("Buscando carrito para usuario: " + idUsuario);
        Optional<CarritoCompra> carrito = carritoCompraRepository.findByIdUsuario(idUsuario);
        System.out.println("Carrito encontrado: " + carrito);

        return carrito.orElseThrow(() -> new CarritoNoEncontradoException(idUsuario));
    }


    /**
     * Busca un producto dentro del carrito de compras de un usuario.
     * @param idUsuario ID del usuario.
     * @param idProductoCarrito ID del producto en el carrito.
     * @return Producto encontrado.
     * @throws CarritoNoEncontradoException si el carrito no existe.
     * @throws ProductoCarritoByIdNoEncontradoException si el producto no está en el carrito.
     */
    
    public ProductoCarrito buscarProductoCarritoPorId(String idUsuario, Integer idProductoCarrito) {
        CarritoCompra carrito = buscarCarritoPorIdUsuario(idUsuario);

        return carrito.getProductos().stream()
                .filter(p -> p.getId().equals(idProductoCarrito))
                .findFirst()
                .orElseThrow(() -> new ProductoCarritoByIdNoEncontradoException(idProductoCarrito));
    }

    /**
     * Obtiene todos los carritos de compras.
     * @return Lista de carritos de compras.
     */
    
    public List<CarritoCompra> obtenerTodosLosCarritos() {
        return carritoCompraRepository.findAll();
    }
}
