package com.api.bigu;

import com.api.bigu.user.UserRepository;
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
    public CommandLineRunner commandLineRunner() {
        return args -> {
            /*
            System.err.println("Admin token: " + authService.authenticate(new AuthenticationRequest("admin@mail.ufcg.edu.br", "1234")));
            System.err.println("Driver 1 token: " + authService.authenticate(new AuthenticationRequest("driver@mail.ufcg.edu.br", "1234")));
            System.err.println("Driver 2 token: " + authService.authenticate(new AuthenticationRequest("driver2@mail.ufcg.edu.br", "12345")));
            System.err.println("Rider 1 token: " + authService.authenticate(new AuthenticationRequest("matheus.rafael@ccc.ufcg.edu.br", "1234")));
            System.err.println("Rider 2 token: " + authService.authenticate(new AuthenticationRequest("rider2@mail.ufcg.edu.br", "12345")));
            */
        };
    }
}
