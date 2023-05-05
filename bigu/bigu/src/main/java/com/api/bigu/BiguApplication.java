package com.api.bigu;

import com.api.bigu.dto.UsuarioDTO;
import com.api.bigu.model.Usuario;
import com.api.bigu.service.UsuarioService;
import org.flywaydb.core.FlywayExecutor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication (scanBasePackages = "com.api.bigu.*")
public class BiguApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiguApplication.class, args);
	}

}
