package com.api.bigu;

import com.api.bigu.dto.auth.RegisterRequest;
import com.api.bigu.dto.car.CarRequest;
import com.api.bigu.dto.car.CarResponse;
import com.api.bigu.dto.ride.RideRequest;
import com.api.bigu.dto.ride.RideResponse;

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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


import java.net.URI;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@SpringBootTest(classes = BiguApplication.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RideTest {
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

    final String baseUrl = "http://localhost:" + port + "/api/v1/rides";

    private String adminToken, driverToken, rider1Token, rider2Token, rider3Token, rider4Token, rider5Token;
    private Map<String, User> registeredUsers = new HashMap<>();
    private User admin, driver, rider1, rider2, rider3, rider4, rider5;
    private Car car;
    private Address addressUFCG1, addressUFCG2, addressUFCG3, addressDriver, addressR1, addressR2, addressR3, addressR4;

    @BeforeEach
    void setUp(){
        buildEntities();
    }

    @Test
    public void testCreateRideSuccess() throws Exception {
        URI uri = new URI(baseUrl);

        RideRequest validRideRequest = RideRequest.builder()
                .carId(car.getCarId())
                .price(1.7)
                .description("Indo para a UFCG amanhã às 7:30. Atrasados serão deixados")
                .dateTime(LocalDateTime.parse("2024-06-16T07:30:00"))
                .startAddressId(addressDriver.getAddressId())
                .goingToCollege(Boolean.TRUE)
                .destinationAddressId(addressUFCG1.getAddressId())
                .numSeats(4)
                .toWomen(Boolean.FALSE)
                .build();

        RideResponse expectedResponse = RideResponse.builder()
                .start(addressMapper.toAddressResponse(addressDriver))
                .dateTime(LocalDateTime.parse("2024-06-16T07:30:00"))
                .description("Indo para a UFCG amanhã às 7:30. Atrasados serão deixados")
                .numSeats(4)
                .price(1.7)
                .car(carMapper.toCarResponse(car))
                .destination(addressMapper.toAddressResponse(addressUFCG1))
                .driver(userMapper.toUserResponse(driver))
                .goingToCollege(Boolean.TRUE)
                .toWomen(Boolean.FALSE)
                .build();

        String authorizationHeader = driverToken;
        System.out.println(authorizationHeader);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        requestHeaders.add("Authorization", authorizationHeader);

        HttpEntity<RideRequest> httpEntity = new HttpEntity<>(validRideRequest, requestHeaders);
        ResponseEntity<RideResponse> actualResponse = this.restTemplate.exchange(uri, HttpMethod.POST, httpEntity, RideResponse.class);

        Assert.assertEquals(ResponseEntity.ok(expectedResponse).getBody(), actualResponse.getBody());
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
