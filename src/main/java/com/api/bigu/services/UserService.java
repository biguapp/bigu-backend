package com.api.bigu.services;

import com.api.bigu.dto.user.UserDTO;
import com.api.bigu.exceptions.UserNotFoundException;
import com.api.bigu.models.User;
import com.api.bigu.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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

    public UserDTO findUserById(Integer userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return new UserDTO(user);
        } else {
            throw new UserNotFoundException("O usuário com Id " + userId + " não foi encontrado.");
        }
    }

    public void deleteById(Integer userId) {
        userRepository.deleteById(userId);
    }

}
