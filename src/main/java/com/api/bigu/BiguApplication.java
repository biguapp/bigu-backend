package com.api.bigu;

import com.api.bigu.auth.AuthenticationService;
import com.api.bigu.auth.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.api.bigu.models.enums.Role.ADMIN;
import static com.api.bigu.models.enums.Role.USER;

@SpringBootApplication
public class BiguApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiguApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	) {
		return args -> {
			var admin = RegisterRequest.builder()
					.fullName("Admin")
					.email("admin@mail.ufcg.edu.com")
					.password("password")
					.role(ADMIN)
					.build();
			System.err.println("Admin token: " + service.register(admin).getToken());

			var user = RegisterRequest.builder()
					.fullName("User")
					.email("user@mail.ufcg.edu.com")
					.password("password")
					.role(USER)
					.build();
			System.err.println("User token: " + service.register(user).getToken());

		};
	}

}
