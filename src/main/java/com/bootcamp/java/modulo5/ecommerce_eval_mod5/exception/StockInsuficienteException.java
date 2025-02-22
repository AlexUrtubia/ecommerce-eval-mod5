package com.bootcamp.java.modulo5.ecommerce_eval_mod5.exception;

public class StockInsuficienteException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public StockInsuficienteException(String prudoctName) {
		super("Sin stock suficiente para "+prudoctName);
	}

}
