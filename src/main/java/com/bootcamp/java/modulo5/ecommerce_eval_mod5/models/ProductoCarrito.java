package com.bootcamp.java.modulo5.ecommerce_eval_mod5.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductoCarrito {
    private Integer id;
    private String codigoProducto;
    private Integer cantidad;
    
    @Override
	public String toString() {
		return "ProductoCarrito [id=" + id + ", codigoProducto=" + codigoProducto + "]";
	}
}
