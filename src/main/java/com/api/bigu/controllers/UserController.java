package com.api.bigu.controllers;

import com.api.bigu.config.JwtService;
import com.api.bigu.dto.user.UserDTO;
import com.api.bigu.exceptions.UserNotFoundException;
import com.api.bigu.models.User;
import com.api.bigu.services.UserService;
import com.api.bigu.util.errors.AuthError;
import com.api.bigu.util.errors.CustomErrorType;
import com.api.bigu.util.errors.UserError;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllUsers(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
            User admin = userService.findUserById(userId);
            if (jwtService.isTokenValid(jwtService.parse(authorizationHeader), admin)) {
                return ResponseEntity.ok(userService.getAllUsers());
            } else {
                return UserError.userNotAnAdministrator();
            }
        } catch (UserNotFoundException uNFE) {
            return UserError.userNotFoundError();
        } catch (ExpiredJwtException eJE) {
            return AuthError.tokenExpiredError();
        }
    }

        @GetMapping("/self")
    public ResponseEntity<?> getSelf(@RequestHeader("Authorization") String authorizationHeader) {
        Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
        try {
            return ResponseEntity.ok(new UserDTO(userService.findUserById(userId)));
        } catch (UserNotFoundException e) {
            return UserError.userNotFoundError();
        }
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteSelf(@RequestHeader("Authorization") String authorizationHeader) {
        Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mail/{userEmail}")
    public ResponseEntity<?> searchByEmail(@PathVariable String userEmail) {
        try {
            UserDTO user = new UserDTO(userService.findUserByEmail(userEmail).get());
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e){
            return UserError.userNotFoundError();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> searchById(@PathVariable Integer userId) {
        try {
            UserDTO user = new UserDTO(userService.findUserById(userId));
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return UserError.userNotFoundError();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor", e);
        }
    }
}
