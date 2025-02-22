package com.bootcamp.java.modulo5.ecommerce_eval_mod5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
@ComponentScan(basePackages = "com.bootcamp.java.modulo5.ecommerce_eval_mod5")

public class EcommerceEvalMod5Application {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceEvalMod5Application.class, args);
	}

	@Bean
	OpenAPI openAPI() {
		return new OpenAPI().info(new Info().title("Evaluación Módulo 5").version("1.0.0").description(
				"Este proyecto implementa una API REST que utiliza MongoDB para gestionar carritos de compra y PostgreSQL para procesar transacciones, órdenes y detalles de compra. La solución sigue principios SOLID y está documentada con Swagger para facilitar su exploración y prueba."));
	}
}
