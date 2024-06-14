package com.kike.gestorinventario.gestor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.kike.gestorinventario.gestor")
public class GestorInventarioBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestorInventarioBackendApplication.class, args);
	}

}
