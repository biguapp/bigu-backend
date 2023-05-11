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
					.password("password")
					.role("ADMIN")
					.build();
			System.err.println("Admin token: " + authService.register(admin).getToken());

			var driver = RegisterRequest.builder()
					.fullName("Driver")
					.email("driver@mail.ufcg.edu.br")
					.phoneNumber("222222222")
					.password("password")
					.role("USER")
					.userType("DRIVER")
					.build();
			System.err.println("Driver token: " + authService.register(driver).getToken());

			var rider = RegisterRequest.builder()
					.fullName("Rider")
					.email("rider@mail.ufcg.edu.br")
					.phoneNumber("333333333")
					.password("password")
					.role("USER")
					.userType("RIDER")
					.build();
			System.err.println("Rider token: " + authService.register(rider).getToken());

			var car1 = Car.builder()
					.user(userService.findUserByEmail("driver@mail.ufcg.edu.br").get())
					.brand("Chevrolet")
					.model("Onix")
					.year(2015)
					.color("Preto")
					.plate("KGU7E07")
					.build();

			//carService.addCarToUser(userService.findUserByEmail("driver@mail.ufcg.edu.br").get().getUserId(), car1);


		};
	}
}
