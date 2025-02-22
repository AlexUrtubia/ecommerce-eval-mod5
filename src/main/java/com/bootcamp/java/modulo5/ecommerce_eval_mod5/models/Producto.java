package com.bootcamp.java.modulo5.ecommerce_eval_mod5.models;

import java.math.BigDecimal;

import com.bootcamp.java.modulo5.ecommerce_eval_mod5.exception.StockInsuficienteException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "productos")
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String codigoProducto;
    private BigDecimal precioUnitario;
    private Integer stock;
    private String nombre;
	
    public void validarStock(int cantidad) {
        if (this.stock < cantidad) {
            throw new StockInsuficienteException(this.nombre);
        }
    }
    
    public void reducirStock(int cantidad) {
        validarStock(cantidad);
        this.stock -= cantidad;
    }

}
