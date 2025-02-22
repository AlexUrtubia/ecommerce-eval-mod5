package com.bootcamp.java.modulo5.ecommerce_eval_mod5.exception;

public class CarritoNoEncontradoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public CarritoNoEncontradoException(String idUsuario) {
        super("No se encontró un carrito para el usuario con ID: " + idUsuario);
    }
}