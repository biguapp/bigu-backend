package com.api.bigu;

import com.api.bigu.controllers.RideController;
import com.api.bigu.dto.auth.RegisterRequest;
import com.api.bigu.dto.car.CarRequest;
import com.api.bigu.dto.car.CarResponse;
import com.api.bigu.dto.ride.RideRequest;
import com.api.bigu.dto.ride.RideResponse;
import com.api.bigu.exceptions.CarNotFoundException;
import com.api.bigu.exceptions.InvalidTimeException;
import com.api.bigu.exceptions.NoCarsFoundException;
import com.api.bigu.exceptions.UserNotFoundException;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;


import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = BiguApplication.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RideTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    RideService rideService;

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
    RideController rideController;

    @Autowired
    CarMapper carMapper;

    @Autowired
    AddressMapper addressMapper;

    @Autowired
    UserMapper userMapper;

    final String baseUrl = "http://localhost:" + port + "/api/v1/rides";

    RegisterRequest adminRequest, driverRequest, rider1Request, rider2Request, rider3Request, rider4Request, rider5Request;

    CarRequest carRequest;
    String adminToken, driverToken, rider1Token, rider2Token, rider3Token, rider4Token, rider5Token;
    User admin, driver, rider1, rider2, rider3, rider4, rider5;
    Car car;
    Address addressUFCG1, addressUFCG2, addressUFCG3, addressDriver, addressR1, addressR2, addressR3, addressR4;

    @BeforeEach
    void setUp(){
        adminRequest = RegisterRequest.builder()
                .fullName("Admin")
                .email("admin@mail.ufcg.edu.br")
                .sex("M")
                .phoneNumber("111111111")
                .password("1234")
                .role("ADMIN")
                .build();
       adminToken = authenticationService.register(adminRequest).getToken();

       driverRequest = RegisterRequest.builder()
                .fullName("Driver de Araujo")
                .email("driver@mail.ufcg.edu.br")
                .sex("F")
                .phoneNumber("222222222")
                .password("1234")
                .role("USER")
                .build();
        driverToken = authenticationService.register(driverRequest).getToken();
        driver = userRepository.findByEmail(driverRequest.getEmail()).get();

        rider1Request = RegisterRequest.builder()
                .fullName("Rider1 Silva")
                .email("matheus.rafael@ccc.ufcg.edu.br")
                .sex("F")
                .phoneNumber("333333333")
                .password("1234")
                .role("USER")
                .build();
        rider1Token = authenticationService.register(rider1Request).getToken();
        rider1 = userRepository.findByEmail(rider1Request.getEmail()).get();

        carRequest = CarRequest.builder()
                .brand("Chevrolet")
                .plate("KGU7E07")
                .color("Prata")
                .model("Onix")
                .modelYear("2019")
                .build();
        CarResponse carResponse = carService.addCarToUser(driver.getUserId(), carRequest);
        car = carRepository.findById(carResponse.getCarId()).get();

        addressUFCG1 = Address.builder()
                .nickname("UFCG - Frente")
                .postalCode("58429900")
                .state("PB")
                .city("Campina Grande")
                .district("Universitário")
                .street("Rua Aprígio Veloso")
                .number("882")
                .complement("Entrada principal da UFCG")
                .build();
        addressRepository.save(addressUFCG1);

        addressDriver = Address.builder()
                .nickname("Casa")
                .postalCode("58433264")
                .state("PB")
                .city("Campina Grande")
                .district("Malvinas")
                .street("Rua Exemplo")
                .number("284")
                .userId(driver.getUserId())
                .build();
        userService.addAddressToUser(addressDriver, driver.getUserId());
        addressRepository.save(addressDriver);

        addressR1 = Address.builder()
                .nickname("Trabalho")
                .postalCode("58400165")
                .state("PB")
                .city("Campina Grande")
                .district("Centro")
                .street("Rua Teste")
                .number("123")
                .userId(rider1.getUserId())
                .build();
        userService.addAddressToUser(addressR1, rider1.getUserId());
        addressRepository.save(addressR1);
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

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        requestHeaders.add("Authorization", authorizationHeader);

        HttpEntity<RideRequest> httpEntity = new HttpEntity<>(validRideRequest, requestHeaders);
        ResponseEntity<RideResponse> actualResponse = this.restTemplate.exchange(uri, HttpMethod.POST, httpEntity, RideResponse.class);

        Assert.assertEquals(ResponseEntity.ok(expectedResponse).getBody(), actualResponse.getBody());
    }

}
