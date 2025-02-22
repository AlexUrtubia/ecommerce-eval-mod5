package com.bootcamp.java.modulo5.ecommerce_eval_mod5.repository.mongodb;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bootcamp.java.modulo5.ecommerce_eval_mod5.models.CarritoCompra;

@Repository
public interface CarritoCompraRepository extends MongoRepository<CarritoCompra, String> {

    Optional<CarritoCompra> findByIdUsuario(String idUsuario);
	
}
