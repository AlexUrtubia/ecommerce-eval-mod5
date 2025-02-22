package com.bootcamp.java.modulo5.ecommerce_eval_mod5.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bootcamp.java.modulo5.ecommerce_eval_mod5.models.ProductoCarrito;

public interface ProductoCarritoRepository extends MongoRepository<ProductoCarrito, Integer>{

}
