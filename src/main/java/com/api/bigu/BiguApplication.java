package com.api.bigu;

import com.api.bigu.models.Address;
import com.api.bigu.models.Car;
import com.api.bigu.repositories.AddressRepository;
import com.api.bigu.repositories.CarRepository;
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
			AuthenticationService authService, AddressRepository addressRepository, CarRepository carRepository, UserService userService
	) {
		return args -> {
			var admin = RegisterRequest.builder()
					.fullName("Admin")
					.email("admin@mail.ufcg.edu.br")
					.sex("M")
					.phoneNumber("111111111")
					.password("1234")
					.role("ADMIN")
					.build();
			System.err.println("Admin Registered");
			System.err.println("Admin token: " + authService.register(admin).getToken());

			var driver = RegisterRequest.builder()
					.fullName("Driver")
					.email("driver@mail.ufcg.edu.br")
					.sex("F")
					.phoneNumber("222222222")
					.password("1234")
					.role("USER")
					.build();
			System.err.println("User 1 registered");
			System.err.println("User token: " + authService.register(driver).getToken());

			var rider = RegisterRequest.builder()
					.fullName("Rider")
					.email("rider@mail.ufcg.edu.br")
					.sex("F")
					.phoneNumber("333333333")
					.password("1234")
					.role("USER")
					.build();
			System.err.println("User 2 registered");
			System.err.println("User 2 token: " + authService.register(rider).getToken());

			var car = Car.builder()
					.brand("Ford")
					.plate("KGU7E07")
					.color("Prata")
					.model("Mustang")
					.user(userService.findUserByEmail("driver@mail.ufcg.edu.br").get())
					.modelYear(2023)
					.build();
			carRepository.save(car);

			System.err.println("Car 1 registered");

			var addressUFCG = Address.builder()
					.nickname("UFCG")
					.postalCode("58429900")
					.state("PB")
					.city("Campina Grande")
					.district("Universitário")
					.street("Rua Aprígio Veloso")
					.number("882")
					.userId(userService.findUserByEmail("admin@mail.ufcg.edu.br").get().getUserId())
					.build();
			addressRepository.save(addressUFCG);

			var address = Address.builder()
					.nickname("Casa")
					.postalCode("58433264")
					.state("PB")
					.city("Campina Grande")
					.district("Malvinas")
					.street("Rua Exemplo")
					.number("284")
					.userId(userService.findUserByEmail("driver@mail.ufcg.edu.br").get().getUserId())
					.build();
			addressRepository.save(address);



		};
	}
}
