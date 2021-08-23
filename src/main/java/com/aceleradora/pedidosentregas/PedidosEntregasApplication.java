package com.aceleradora.pedidosentregas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity(debug = true)
@SpringBootApplication
public class PedidosEntregasApplication {

	public static void main(String[] args) {
		SpringApplication.run(PedidosEntregasApplication.class, args);
	}

}
