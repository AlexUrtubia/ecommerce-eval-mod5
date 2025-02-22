package com.bootcamp.java.modulo5.ecommerce_eval_mod5.exception;

public class ProductoCarritoByIdNoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductoCarritoByIdNoEncontradoException(Integer productoId) {
		super("Producto no encontrado con id: " + productoId);
	}
}
