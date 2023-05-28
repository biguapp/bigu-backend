package com.api.bigu.services;

import com.api.bigu.dto.address.AddressResponse;
import com.api.bigu.dto.auth.RegisterRequest;
import com.api.bigu.dto.user.UserDTO;
import com.api.bigu.exceptions.UserNotFoundException;
import com.api.bigu.models.Address;
import com.api.bigu.models.Car;
import com.api.bigu.models.User;
import com.api.bigu.models.enums.Role;
import com.api.bigu.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Integer buildUser(RegisterRequest requestUser) {
        User user = User.builder()
                .fullName(requestUser.getFullName())
                .email(requestUser.getEmail())
                .phoneNumber(requestUser.getPhoneNumber())
                .password(passwordEncoder.encode(requestUser.getPassword()))
                .role(Role.valueOf(requestUser.getRole().toUpperCase()))
                .build();
        this.registerUser(user);
        return user.getUserId();
    }

//    public Integer authUser(AuthenticationRequest requestUser){
//        User user = User.builder()
//                .fullName(requestUser.getFullName())
//                .email(requestUser.getEmail())
//                .matricula(requestUser.getMatricula())
//                .phoneNumber(requestUser.getPhoneNumber())
//                .password(passwordEncoder.encode(requestUser.getPassword()))
//                .role(Role.valueOf(requestUser.getRole().toUpperCase()))
//                .build();
//        this.registerUser(user);
//        return user.getUserId();
//    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Integer userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException("O usuário com Id " + userId + " não foi encontrado.");
        }
    }

    public void deleteById(Integer userId) {

    	//deletamos as caronas em que o user foi motorista ou passageiro
    	//rideService.deleteByUserId(userId);

        userRepository.deleteById(userId);
    }

    public Optional<User> findUserByEmail(String userEmail) throws UserNotFoundException {
        Optional<User> user = userRepository.findByEmail(userEmail);
        if (user.isPresent()) {
            return user;
        } else {
            throw new UserNotFoundException("O usuário com email " + userEmail + " não foi encontrado.");
        }
    }

    public void addAddressToUser(Address address, Integer userId){
        User user = userRepository.findById(userId).get();
        user.getAddresses().put(address.getNickname(), address);
    }

//    public void updateUser(User user) {
//        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
//            userRepository.save(user);
//        }
//
//    }

    public boolean isBlocked(String email) {
        return userRepository.findByEmail(email).get().isAccountNonLocked();
    }
}
