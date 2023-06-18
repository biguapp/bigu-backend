package com.api.bigu;

import com.api.bigu.dto.auth.AuthenticationResponse;
import com.api.bigu.dto.auth.RegisterRequest;
import com.api.bigu.dto.car.CarRequest;
import com.api.bigu.dto.car.CarResponse;
import com.api.bigu.dto.ride.RideRequest;
import com.api.bigu.dto.ride.RideResponse;

import com.api.bigu.dto.user.UserResponse;
import com.api.bigu.models.Address;
import com.api.bigu.models.Car;
import com.api.bigu.models.User;
import com.api.bigu.repositories.AddressRepository;
import com.api.bigu.repositories.CarRepository;
import com.api.bigu.repositories.UserRepository;
import com.api.bigu.services.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;


import java.time.LocalDateTime;
import java.util.Arrays;


@SpringBootTest(classes = BiguApplication.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EntityBuilder entityBuilder;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    CarService carService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CarMapper carMapper;

    @Autowired
    AddressMapper addressMapper;

    @Autowired
    UserMapper userMapper;

    private String adminToken, driverToken, rider1Token, rider2Token, rider3Token, rider4Token, rider5Token;
    private User admin, driver, rider1, rider2, rider3, rider4, rider5;
    private Car car;
    private Address addressUFCG1, addressUFCG2, addressUFCG3, addressDriver, addressR1, addressR2, addressR3, addressR4;

    @BeforeEach
    void setUp(){
        buildEntities();
    }

    @Test
    public void testCreateUserSuccess() {
        RegisterRequest validRegisterRequest = entityBuilder.buildUser("Usu de Teste",
                "usu.teste@ccc.ufcg.edu.br", "M", "999999999", "123", "USER");
        UserResponse userResponse = UserResponse.builder()
                .userId(8)
                .fullName("Usu de Teste")
                .email("usu.teste@ccc.ufcg.edu.br")
                .sex("M")
                .phoneNumber("999999999")
                .build();


        HttpEntity<RegisterRequest> httpEntity = new HttpEntity<>(validRegisterRequest);
        ResponseEntity<AuthenticationResponse> actualResponse = this.restTemplate.exchange("http://localhost:" + port + "/api/v1/auth/register", HttpMethod.POST, httpEntity, AuthenticationResponse.class);

        Assert.assertEquals(userResponse, actualResponse.getBody().getUserResponse());
    }

    private void buildEntities() {
        RegisterRequest adminRequest = entityBuilder.buildUser("Admin","admin@mail.ufcg.edu.br", "M", "111111111", "1234", "ADMIN");
        adminToken = authenticationService.register(adminRequest).getToken();
        admin = userRepository.findByEmail(adminRequest.getEmail()).get();

        RegisterRequest driverRequest = entityBuilder.buildUser("Driver de Araujo", "driver@mail.ufcg.edu.br", "M", "222222222", "1234", "USER");
        driverToken = authenticationService.register(driverRequest).getToken();
        driver = userRepository.findByEmail(driverRequest.getEmail()).get();

        RegisterRequest rider1Request = entityBuilder.buildUser("Rider1 Silva", "rider1.silva@ccc.ufcg.edu.br", "F", "333333333", "1234", "USER");
        rider1Token = authenticationService.register(rider1Request).getToken();
        rider1 = userRepository.findByEmail(rider1Request.getEmail()).get();

        RegisterRequest rider2Request = entityBuilder.buildUser("Rider2 Silva", "rider2.silva@ccc.ufcg.edu.br", "F", "444444444", "1234", "USER");
        rider2Token = authenticationService.register(rider2Request).getToken();
        rider2 = userRepository.findByEmail(rider2Request.getEmail()).get();

        RegisterRequest rider3Request = entityBuilder.buildUser("Rider3 Silva", "rider3.rafaela@ccc.ufcg.edu.br", "F", "555555555", "1234", "USER");
        rider3Token = authenticationService.register(rider3Request).getToken();
        rider3 = userRepository.findByEmail(rider3Request.getEmail()).get();

        RegisterRequest rider4Request = entityBuilder.buildUser("Rider4 Silva", "rider4.rafaela@ccc.ufcg.edu.br", "F", "666666666", "1234", "USER");
        rider4Token = authenticationService.register(rider4Request).getToken();
        rider4 = userRepository.findByEmail(rider4Request.getEmail()).get();

        RegisterRequest rider5Request = entityBuilder.buildUser("Rider5 Silva", "rider5.rafaela@ccc.ufcg.edu.br", "F", "77777777", "1234", "USER");
        rider5Token = authenticationService.register(rider5Request).getToken();
        rider5 = userRepository.findByEmail(rider5Request.getEmail()).get();

        CarRequest carRequest = entityBuilder.buildCar("Chevrolet", "KGU7E07", "Prata", "Onix", "2019");
        CarResponse carResponse = carService.addCarToUser(driver.getUserId(), carRequest);
        car = carRepository.findById(carResponse.getCarId()).get();

        addressUFCG1 = entityBuilder.buildAddress("UFCG - Frente", "58429900", "PB", "Campina Grande", "Universitário", "Rua Aprígio Veloso", "882", "Entrada principal da UFCG",null);
        addressRepository.save(addressUFCG1);

        addressDriver = entityBuilder.buildAddress("Casa", "58433264", "PB", "Campina Grande", "Malvinas", "Rua Exemplo", "222", "Portão à esquerda", driver.getUserId());
        userService.addAddressToUser(addressDriver, driver.getUserId());

        addressR1 = entityBuilder.buildAddress("Trabalho", "58400165", "PB", "Campina Grande", "Centro", "Rua Teste", "123", "Primeiro andar", rider1.getUserId());
        userService.addAddressToUser(addressR1, rider1.getUserId());
    }
}
