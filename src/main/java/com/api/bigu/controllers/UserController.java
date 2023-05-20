package com.api.bigu.controllers;

import com.api.bigu.config.JwtService;
import com.api.bigu.dto.user.UserDTO;
import com.api.bigu.exceptions.UserNotFoundException;
import com.api.bigu.models.User;
import com.api.bigu.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDTO> searchById(@PathVariable Integer userId) {

        try {
            UserDTO usuario = new UserDTO(userService.findUserById(userId));
            return ResponseEntity.ok(usuario);

        } catch (UserNotFoundException e) {
            // tratar o caso em que o usuário não é encontrado
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado", e);
        } catch (Exception e) {
            // tratar outros tipos de exceção
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor", e);
        }
    }

    @GetMapping("/user/")
    public ResponseEntity<?> getSelf(@RequestHeader("Authorization") String authorizationHeader) {
        Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
        try {
            return ResponseEntity.ok(new UserDTO(userService.findUserById(userId)));
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error retrieving user", e);
        }
        }

    @DeleteMapping("/user/")
    public ResponseEntity<Void> deleteSelf(@RequestHeader("Authorization") String authorizationHeader) {
        Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
}
