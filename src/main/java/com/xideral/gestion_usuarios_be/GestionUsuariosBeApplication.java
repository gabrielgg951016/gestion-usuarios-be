package com.xideral.gestion_usuarios_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {org.springframework.boot.actuate.autoconfigure.web.server.ManagementContextAutoConfiguration.class})
public class GestionUsuariosBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionUsuariosBeApplication.class, args);
	}

}
