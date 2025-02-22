package com.bootcamp.java.modulo5.ecommerce_eval_mod5.exception;

public class OrdenCompraNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public OrdenCompraNotFoundException(Integer idOrden) {
		super("Orden no encontrada con ID: " + idOrden);
	}
}
