package com.api.bigu.services;

import com.api.bigu.models.User;
import com.api.bigu.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);
    }

    public void deleteById(Integer userId) {
        userRepository.deleteById(userId);
    }

}
