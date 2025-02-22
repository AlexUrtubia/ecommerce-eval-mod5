package com.bootcamp.java.modulo5.ecommerce_eval_mod5.repository.postgresql;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bootcamp.java.modulo5.ecommerce_eval_mod5.models.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
	Optional<Producto> findById(Integer id);
}
