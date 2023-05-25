package com.api.bigu;

import com.api.bigu.models.Car;
import com.api.bigu.services.AuthenticationService;
import com.api.bigu.dto.auth.RegisterRequest;
import com.api.bigu.services.CarService;
import com.api.bigu.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class BiguApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiguApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService authService, CarService carService, UserService userService
	) {
		return args -> {
			var admin = RegisterRequest.builder()
					.fullName("Admin")
					.email("admin@mail.ufcg.edu.br")
					.phoneNumber("111111111")
					.password("1234")
					.role("ADMIN")
					.build();
			System.err.println("Admin Registered");
			System.err.println("Admin token: " + authService.register(admin).getToken());

			var driver = RegisterRequest.builder()
					.fullName("Driver")
					.email("driver@mail.ufcg.edu.br")
					.phoneNumber("222222222")
					.password("1234")
					.role("USER")
					.build();
			System.err.println("User 1 registered");
			System.err.println("User token: " + authService.register(driver).getToken());

			var rider = RegisterRequest.builder()
					.fullName("Rider")
					.email("rider@mail.ufcg.edu.br")
					.phoneNumber("333333333")
					.password("1234")
					.role("USER")
					.build();
			System.err.println("User 2 registered");
			System.err.println("User 2 token: " + authService.register(rider).getToken());


		};
	}
}
