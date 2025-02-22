package com.bootcamp.java.modulo5.ecommerce_eval_mod5.repository.postgresql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bootcamp.java.modulo5.ecommerce_eval_mod5.models.OrdenCompra;

@Repository
public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Integer> {

}
