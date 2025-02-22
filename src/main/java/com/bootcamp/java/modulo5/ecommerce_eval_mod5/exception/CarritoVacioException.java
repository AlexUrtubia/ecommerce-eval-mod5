package com.bootcamp.java.modulo5.ecommerce_eval_mod5.exception;

public class CarritoVacioException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CarritoVacioException() {
		super("El carrito está vacío");
	}

}
