package com.api.bigu;
import com.api.bigu.dto.auth.RegisterRequest;
import com.api.bigu.dto.car.CarRequest;
import com.api.bigu.models.Address;
import org.springframework.stereotype.Component;

@Component
public class EntityBuilder {
    public CarRequest buildCar(String brand, String plate, String color, String model, String modelYear) {
        CarRequest request = CarRequest.builder()
                .brand(brand)
                .plate(plate)
                .color(color)
                .model(model)
                .modelYear(modelYear)
                .build();

        return request;
    }

    public RegisterRequest buildUser(String fullName, String email, String sex, String phoneNumber, String password, String role) {
        RegisterRequest request = RegisterRequest.builder()
                .fullName(fullName)
                .email(email)
                .sex(sex)
                .phoneNumber(phoneNumber)
                .password(password)
                .role(role)
                .build();

        return request;

    }

    public Address buildAddress(String nickname, String postalCode, String state, String city, String district, String street, String number, String complement, Integer userId) {
        Address address = Address.builder()
                .nickname(nickname)
                .postalCode(postalCode)
                .state(state)
                .city(city)
                .district(district)
                .street(street)
                .number(number)
                .userId(userId)
                .build();

        return address;
    }
}
