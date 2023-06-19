package com.api.bigu;

import com.api.bigu.dto.auth.AuthenticationRequest;
import com.api.bigu.dto.car.CarRequest;
import com.api.bigu.dto.ride.RideRequest;
import com.api.bigu.exceptions.CarNotFoundException;
import com.api.bigu.exceptions.UserNotFoundException;
import com.api.bigu.models.Address;
import com.api.bigu.models.Car;
import com.api.bigu.repositories.AddressRepository;
import com.api.bigu.repositories.CarRepository;
import com.api.bigu.repositories.UserRepository;
import com.api.bigu.services.*;
import com.api.bigu.dto.auth.RegisterRequest;
import com.api.bigu.util.errors.CarError;
import com.api.bigu.util.errors.UserError;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;


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
            AuthenticationService authService
    ) {
        return args -> {
            System.err.println("Admin token: " + authService.authenticate(new AuthenticationRequest("admin@mail.ufcg.edu.br", "1234")));
            System.err.println("Driver 1 token: " + authService.authenticate(new AuthenticationRequest("driver@mail.ufcg.edu.br", "1234")));
            System.err.println("Driver 2 token: " + authService.authenticate(new AuthenticationRequest("driver2@mail.ufcg.edu.br", "12345")));
            System.err.println("Rider 1 token: " + authService.authenticate(new AuthenticationRequest("matheus.rafael@ccc.ufcg.edu.br", "1234")));
            System.err.println("Rider 2 token: " + authService.authenticate(new AuthenticationRequest("rider2@mail.ufcg.edu.br", "12345")));

        };
    }
}
