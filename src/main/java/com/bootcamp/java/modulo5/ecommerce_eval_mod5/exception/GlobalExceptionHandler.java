package com.bootcamp.java.modulo5.ecommerce_eval_mod5.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CarritoNoEncontradoException.class)
    public ResponseEntity<String> handleCarritoNoEncontradoException(CarritoNoEncontradoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(CarritoVacioException.class)
	public ResponseEntity<String> handleCarritoVacioException(CarritoVacioException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}
    
    @ExceptionHandler(ProductoCarritoByIdNoEncontradoException.class)
    public ResponseEntity<String> handleProductoCarritoNoEncontradoException(ProductoCarritoByIdNoEncontradoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ProductoByIdNoEncontrado.class)
	public ResponseEntity<String> handleProductoNoEncontradoException(ProductoByIdNoEncontrado ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
    
    @ExceptionHandler(StockInsuficienteException.class)
    public ResponseEntity<String> handleStockInsuficienteException(StockInsuficienteException ex) {
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
    @ExceptionHandler(OrdenCompraNotFoundException.class)
	public ResponseEntity<String> handleOrdenCompraNoEncontradaException(OrdenCompraNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
}
