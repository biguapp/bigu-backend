package com.api.bigu;

import com.api.bigu.models.enums.UserType;
import com.api.bigu.services.AuthenticationService;
import com.api.bigu.dto.auth.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
					.email("admin@mail.ufcg.edu.br")
					.phoneNumber("111111111")
					.password("password")
					.role("ADMIN")
					.build();
			System.err.println("Admin token: " + service.register(admin).getToken());

			var driver = RegisterRequest.builder()
					.fullName("Driver")
					.email("driver@mail.ufcg.edu.br")
					.phoneNumber("222222222")
					.password("password")
					.role("USER")
					.userType("DRIVER")
					.build();
			System.err.println("Driver token: " + service.register(driver).getToken());

			var rider = RegisterRequest.builder()
					.fullName("Rider")
					.email("rider@mail.ufcg.edu.br")
					.phoneNumber("333333333")
					.password("password")
					.role("USER")
					.userType("RIDER")
					.build();
			System.err.println("Rider token: " + service.register(rider).getToken());

		};
	}

}
