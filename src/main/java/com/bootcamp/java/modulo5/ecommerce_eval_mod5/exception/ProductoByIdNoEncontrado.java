package com.bootcamp.java.modulo5.ecommerce_eval_mod5.exception;

public class ProductoByIdNoEncontrado extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductoByIdNoEncontrado(Integer id) {
		super("Producto no encontrado con id: " + id);
	}
}
