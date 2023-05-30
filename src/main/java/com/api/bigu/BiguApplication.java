package com.api.bigu;

import com.api.bigu.dto.car.CarRequest;
import com.api.bigu.exceptions.UserNotFoundException;
import com.api.bigu.models.Address;
import com.api.bigu.models.Car;
import com.api.bigu.repositories.AddressRepository;
import com.api.bigu.repositories.CarRepository;
import com.api.bigu.repositories.UserRepository;
import com.api.bigu.services.AuthenticationService;
import com.api.bigu.dto.auth.RegisterRequest;
import com.api.bigu.services.CarMapper;
import com.api.bigu.services.CarService;
import com.api.bigu.services.UserService;
import com.api.bigu.util.errors.UserError;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class BiguApplication {
	private final UserRepository userRepository;

	public BiguApplication(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
        SpringApplication.run(BiguApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService authService, AddressRepository addressRepository, UserService userService, CarService carService
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
                    .email("italo.ramalho@ccc.ufcg.edu.br")
                    .sex("F")
                    .phoneNumber("222222222")
                    .password("1234")
                    .role("USER")
                    .build();
            System.err.println("User 1 registered");
            System.err.println("User 1 token: " + authService.register(driver).getToken());

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


            CarRequest car = CarRequest.builder()
                    .brand("Ford")
                    .plate("KGU7E07")
                    .color("Prata")
                    .model("Mustang")
                    .modelYear(2023)
                    .build();
            try {
                carService.addCarToUser(userRepository.findByEmail("italo.ramalho@ccc.ufcg.edu.br").get().getUserId(), car);
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }
            System.err.println("Car 1 registered");

            Address addressUFCG1 = Address.builder()
                    .nickname("UFCG - Frente")
                    .postalCode("58429900")
                    .state("PB")
                    .city("Campina Grande")
                    .district("Universitário")
                    .street("Rua Aprígio Veloso")
                    .number("882")
                    .complement("Entrada principal da UFCG")
                    .userId(userRepository.findByEmail("admin@mail.ufcg.edu.br").get().getUserId())
                    .build();
            userService.addAddressToUser(addressUFCG1, userRepository.findByEmail("admin@mail.ufcg.edu.br").get().getUserId());
            addressRepository.save(addressUFCG1);

            Address addressUFCG2 = Address.builder()
                    .nickname("UFCG - CEEI")
                    .postalCode("58429900")
                    .state("PB")
                    .city("Campina Grande")
                    .district("Universitário")
                    .street("Rua Aprígio Veloso")
                    .number("882")
                    .complement("Entrada do Centro de Engenharia Elétrica e Informática")
                    .userId(userRepository.findByEmail("admin@mail.ufcg.edu.br").get().getUserId())
                    .build();
            userService.addAddressToUser(addressUFCG2, userRepository.findByEmail("admin@mail.ufcg.edu.br").get().getUserId());
            addressRepository.save(addressUFCG2);

            Address addressUFCG3 = Address.builder()
                    .nickname("UFCG - Humanas")
                    .postalCode("58429900")
                    .state("PB")
                    .city("Campina Grande")
                    .district("Universitário")
                    .street("Rua Aprígio Veloso")
                    .number("882")
                    .complement("Entrada por trás do bloco BG")
                    .userId(userRepository.findByEmail("admin@mail.ufcg.edu.br").get().getUserId())
                    .build();
            userService.addAddressToUser(addressUFCG3, userRepository.findByEmail("admin@mail.ufcg.edu.br").get().getUserId());
            addressRepository.save(addressUFCG3);


            Address address = Address.builder()
                    .nickname("Casa")
                    .postalCode("58433264")
                    .state("PB")
                    .city("Campina Grande")
                    .district("Malvinas")
                    .street("Rua Exemplo")
                    .number("284")
                    .userId(userRepository.findByEmail("italo.ramalho@ccc.ufcg.edu.br").get().getUserId())
                    .build();
            userService.addAddressToUser(address, userRepository.findByEmail("italo.ramalho@ccc.ufcg.edu.br").get().getUserId());
            addressRepository.save(address);

        };
    }
}
