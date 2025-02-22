package com.bootcamp.java.modulo5.ecommerce_eval_mod5.models;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "User")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarritoCompra {

    @Id
    private String id;
    private String idUsuario;
    private List<ProductoCarrito> productos;
}
