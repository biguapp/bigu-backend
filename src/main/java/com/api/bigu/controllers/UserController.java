package com.api.bigu.controllers;

import com.api.bigu.models.User;
import com.api.bigu.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> buscarPorMatricula(@PathVariable Integer userId) {

        try {
            User usuario = userService.findUserById(userId);
            // TODO retornar o DTO do usuário
            return ResponseEntity.ok(usuario);

        } catch (Exception e) {
            // TODO criar um exception handle
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> excluirPorMatricula(@PathVariable Integer userId) {
        userService.deleteById(userId);

        return ResponseEntity.noContent().build();
    }
}
